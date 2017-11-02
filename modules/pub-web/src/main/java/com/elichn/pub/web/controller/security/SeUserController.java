package com.elichn.pub.web.controller.security;

import com.elichn.pub.core.constant.CommonConstats;
import com.elichn.pub.core.constant.UserTypeEnum;
import com.elichn.pub.core.model.pub.bvo.SeRoleTreeBvo;
import com.elichn.pub.core.model.pub.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.pub.pojo.security.SeRole;
import com.elichn.pub.core.model.pub.pojo.security.SeUser;
import com.elichn.pub.core.model.pub.pojo.security.SeUserRoleKey;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.controller.BaseController;
import com.elichn.pub.web.util.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeUserController</p>
 * <p>Description: SeUserController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/user")
public class SeUserController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(SeUserController.class);

    private final static String PREFIX = "/security/user";

    @Autowired
    private SeRoleService seRoleService;
    @Autowired
    private SeUserService seUserService;
    @Autowired
    private SecurityManager securityManager;

    /**
     * editUser editUser
     *
     * @param userId userId
     * @param model  model
     * @return String
     */
    @RequestMapping(value = "/editUser")
    public String editUser(@RequestParam(value = "id", defaultValue = "") Integer userId,
                           Model model) {
        if (userId != null) {
            if (userId == 1) {
                return "/security/accessDenied";
            } else {
                SeUser user = seUserService.selectUserById(userId);
                if (user == null) {
                    return PREFIX + "/editUser";
                }
                model.addAttribute("user", user);
                // 没有角色的用户
                List<SeRole> list = seRoleService.selectRoleListByUser(userId.toString());
                if (list.size() == 0) {
                    return PREFIX + "/editUser";
                }
            }
        }
        return PREFIX + "/editUser";
    }

    /**
     * userManager 用户管理入口页
     *
     * @param model   model
     * @param request request
     * @return String
     */
    @RequestMapping(value = "/userManager")
    public String userManager(Model model, HttpServletRequest request) {
        model.addAttribute("roleList", getSubRoleOfCurrentUser());
        model.addAttribute("style", WebUtils.getSessionAttribute(request, CommonConstats.STYLE));
        return PREFIX + "/userManager";
    }

    /**
     * viewUsers  展示用户
     *
     * @param page       page
     * @param pageSize   pageSize
     * @param username   pageSize
     * @param roleName   roleName
     * @param status     status
     * @param userType   userType
     * @param searchType searchType
     * @param model      model
     */
    @RequestMapping(value = "/viewUsers")
    public void viewUsers(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                          @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
                          @RequestParam(value = "username", required = false) String username,
                          @RequestParam(value = "roleName", required = false) String roleName,
                          @RequestParam(value = "status", required = false) Integer status,
                          @RequestParam(value = "userType", required = false) Integer userType,
                          @RequestParam(value = "searchType", required = false, defaultValue = "0") Integer searchType,
                          Model model) {
        List<SeRole> roles = getSubRoleOfCurrentUser();
        Map map = new HashMap(16);
        map.put("username", username);
        map.put("searchType", searchType);
        map.put("roleName", roleName);
        map.put("status", status);
        map.put("userType", userType);
        if (roles != null && roles.size() > 0) {
            List<String> roleNames = new ArrayList<String>();
            for (SeRole r : roles) {
                roleNames.add(r.getRoleName());
            }
            map.put("roleNames", roleNames);
        }
        List<SeUserRoleBvo> userList = seUserService.selectUsersByPage(page, pageSize, map);
        model.addAttribute("userRoleBvoList", userList);
        model.addAttribute("total", seUserService.getRowsByName(map));
    }

    /**
     * addUserView 跳转添加用户页面
     */
    @RequestMapping(value = "/addUserPage", method = RequestMethod.GET)
    public String addUserView(Model model) {
        List<SeRole> roleList = seRoleService.selectRoles();
        model.addAttribute("roleList", roleList);
        return PREFIX + "/addUser";
    }

    /**
     * isUser 判断用户是否存在
     *
     * @param userName userName
     */
    @RequestMapping(value = "/isUser", method = RequestMethod.GET)
    public void isUser(@RequestParam("userName") String userName,
                       HttpServletResponse response) {
        try {
            userName = URLDecoder.decode(userName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.info("{}", e);
        }
        SeUser user = seUserService.selectByName(userName);
        Boolean isUser = false;
        if (user != null) {
            isUser = true;
        }

        returnMsg(response, !isUser);
    }

    /**
     * addUser 添加用户
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(
            Model model, HttpServletRequest request,
            @RequestParam("userName") String userName,
            @RequestParam(value = "cnName", required = false) String cnName,
            @RequestParam("password") String password,
            @RequestParam(value = "roleList", defaultValue = "") String roleList,
            @RequestParam(value = "status", defaultValue = "1") Integer status,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam("descn") String descn) throws IOException {
        if (StringUtils.isBlank(userName) || seUserService.selectByName(userName) != null) {
            model.addAttribute("success", false);
            return;
        }
        SeUser user = new SeUser();
        user.setUsername(userName.trim());
        int passwordLength = 3;
        if (PasswordUtil.isComplexPassword(password) < passwordLength) {
            model.addAttribute("success", false);
            model.addAttribute("msg", "密码强度不够");
        }
        user.setPassword(DigestUtils.md5Hex(password.trim()));
        user.setStatus(status);
        user.setUserType(UserTypeEnum.STANDARD.getTypeValue());
        if (StringUtils.isNotBlank(email)) {
            user.setEmail(email.trim());
        }
        if (StringUtils.isNotBlank(descn)) {
            user.setDescn(descn);
        }
        if (StringUtils.isNotBlank(cnName)) {
            user.setCnName(cnName);
        }
        seUserService.insertUser(user);
        if (StringUtils.isNotBlank(roleList)) {
            SeUser currentUser = seUserService.selectByName(userName);
            ObjectMapper mapper = new ObjectMapper();
            List<Map> value = mapper.readValue(roleList, ArrayList.class);
            for (Map m : value) {
                int roleId = (Integer) m.get("id");
                boolean checked = (Boolean) m.get("checked");
                SeUserRoleKey userRoleKey = new SeUserRoleKey();
                userRoleKey.setRoleId(roleId);
                userRoleKey.setUserId(currentUser.getId());
                if (checked) {
                    seRoleService.insertUserRoleKey(userRoleKey);
                } else {
                    seRoleService.deleteByPrimaryKey(userRoleKey);
                }
            }
        }
        model.addAttribute("success", true);
        writeLog(request, "添加用户", "添加用户(" + userName + ")");
    }

    /**
     * updateUser 编辑用户
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public void updateUser(
            Model model, HttpServletRequest request,
            @RequestParam("id") Integer id,
            @RequestParam(value = "cnName", required = false) String cnName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "descn", required = false) String descn,
            @RequestParam(value = "roleList", defaultValue = "") String roleList) throws IOException {
        if (id == 1) {
            model.addAttribute("success", false);
            return;
        }
        SeUser user = seUserService.selectUserById(id);
        user.setCnName(cnName);
        user.setEmail(email);
        user.setDescn(descn);

        seUserService.updateByPrimaryKey(user);
        if (StringUtils.isNotBlank(roleList)) {
            ObjectMapper mapper = new ObjectMapper();
            List<Map> value = mapper.readValue(roleList, ArrayList.class);
            for (Map m : value) {
                int roleId = (Integer) m.get("id");
                boolean checked = (Boolean) m.get("checked");
                SeUserRoleKey userRoleKey = new SeUserRoleKey();
                userRoleKey.setRoleId(roleId);
                userRoleKey.setUserId(user.getId());
                if (checked) {
                    seRoleService.insertUserRoleKey(userRoleKey);
                } else {
                    seRoleService.deleteByPrimaryKey(userRoleKey);
                }
            }
        }
        model.addAttribute("success", true);
        // writeLog(request, "编辑用户", "编辑用户(" + user.getUsername() + ")");
    }

    /**
     * getRoleTree 查询数据权限
     *
     * @param model model
     */
    @RequestMapping(value = "/getRoleTree")
    public void getRoleTree(Model model,
                            @RequestParam(value = "id", defaultValue = "") Integer userId) {
        List<SeRole> roles = null;
        if (userId != null) {
            // 获取被管理用户的直接角色
            roles = seRoleService.selectRoleListByUser(userId.toString());
        }
        String userName = getUserName();
        if (userName != null) {
            SeUser user = seUserService.selectByName(userName);
            if (user != null) {
                // 获取当前用户的所有角色
                List<SeRole> list = seRoleService.selectRolesByUser(user.getId());
                if (roles != null) {
                    List<Integer> roleIdList = new ArrayList<Integer>();
                    for (SeRole role : roles) {
                        roleIdList.add(role.getId());
                    }

                    // 初始化勾选的角色
                    List<SeRoleTreeBvo> bvos = new ArrayList<SeRoleTreeBvo>();
                    for (SeRole role : list) {
                        SeRoleTreeBvo bvo = SeRoleTreeBvo.copyFromRole(role);
                        if (roleIdList.contains(role.getId())) {
                            bvo.setChecked(true);
                        } else {
                            bvo.setChecked(false);
                        }

                        bvo.setNocheck(false);
                        bvos.add(bvo);
                    }
                    model.addAttribute("list", bvos);
                    return;
                } else {
                    // 初始化勾选的角色
                    List<SeRoleTreeBvo> bvos = new ArrayList<SeRoleTreeBvo>();
                    for (SeRole role : list) {
                        SeRoleTreeBvo bvo = SeRoleTreeBvo.copyFromRole(role);
                        bvo.setNocheck(false);
                        bvos.add(bvo);
                    }
                    model.addAttribute("list", bvos);
                    return;
                }
            }
        }
    }

    /**
     * selectRoles 展示角色
     *
     * @param model model
     */
    @RequestMapping(value = "/selectRoles")
    public void selectRoles(Model model) {
        model.addAttribute("roleList", this.getSubRoleOfCurrentUser());
    }

    /**
     * getSubRoleOfCurrentUser 获取当前用户所具有的子孙角色
     *
     * @return List<SeRole>
     */
    private List<SeRole> getSubRoleOfCurrentUser() {
        SeUser user = seUserService.selectByName(getUserName());
        if (user == null) {
            return null;
        } else {
            return seRoleService.selectRolesByUser(user.getId());
        }
    }

    /**
     * resetPassword resetPassword
     *
     * @param model  model
     * @param userId userId
     */
    @RequestMapping(value = "/resetPassword")
    public void resetPassword(Model model,
                              @RequestParam(value = "id") Integer userId) {
        SeUser user = seUserService.selectUserById(userId);
        if (user != null && user.getUserType() == UserTypeEnum.STANDARD.getTypeValue()) {
            String password = PasswordUtil.randomPassword();
            user.setPassword(DigestUtils.md5Hex(password));
            seUserService.updateUser(user);
            model.addAttribute("msg", password);
        } else if (user != null && user.getUserType() == UserTypeEnum.LDAP.getTypeValue()) {
            model.addAttribute("msg", "LDAP");
        } else {
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * password password
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/password")
    public String password(Model model) {
        return PREFIX + "/password";
    }

    /**
     * updatePassword 修改密码
     *
     * @param model       model
     * @param request     request
     * @param oldPassword oldPassword(经过md5加密)
     * @param newPassword newPassword(经过md5加密)
     */
    @RequestMapping(value = "/updatePassword")
    public void updatePassword(Model model, HttpServletRequest request,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        String userName = getUserName();
        if (StringUtils.isBlank(userName)) {
            model.addAttribute("msg", SESSION_TIME_OUT);
            model.addAttribute("success", FAIL);
            return;
        }
        SeUser user = seUserService.selectByName(userName);
        if (user == null) {
            model.addAttribute("msg", USER_NOT_EXIST);
            model.addAttribute("success", FAIL);
        } else {
            String msg;
            String success;
            if (!oldPassword.toLowerCase().equals(user.getPassword())) {
                msg = "原始密码错误";
                success = FAIL;
            } else {
                try {
                    user.setPassword(newPassword);
                    seUserService.updateUser(user);
                    msg = "更新密码成功！";
                    success = SUCCESS;
                } catch (Exception e) {
                    msg = "更新密码失败！";
                    success = FAIL;
                }
            }
            writeLog(request, "修改密码", "用户" + userName + msg);
            model.addAttribute("msg", msg);
            model.addAttribute("success", success);
        }
    }

    /**
     * setStatus setStatus
     *
     * @param model   model
     * @param request request
     * @param userId  request
     */
    @RequestMapping(value = "/setStatus")
    public void setStatus(Model model, HttpServletRequest request,
                          @RequestParam(value = "id") Integer userId) {
        SeUser user = seUserService.selectUserById(userId);
        if (user != null) {
            if (user.getStatus() == 1) {
                user.setStatus(0);
            } else {
                user.setStatus(1);
            }

            int re = seUserService.updateUser(user);
            if (re > 0) {
                model.addAttribute("msg", SUCCESS);
            } else {
                model.addAttribute("msg", FAIL);
            }
        } else {
            model.addAttribute("msg", USER_NOT_EXIST);
        }
    }

    /**
     * hasPermissions hasPermissions
     *
     * @param model     model
     * @param sessionId sessionId
     */
    @RequestMapping(value = "/hasPermissions")
    public void hasPermissions(Model model,
                               @RequestParam(value = "sessionId") String sessionId) {
        Subject.Builder builder = new Subject.Builder(securityManager);
        builder.sessionId(sessionId);
        Subject subject = builder.buildSubject();
        model.addAttribute("r1", subject.isPermitted("/user:hasPermissions"));
        model.addAttribute("r2", subject.isAuthenticated());
    }

    /**
     * pullUser 拉取用户
     *
     * @param model
     */
    @RequestMapping(value = "/pullUser")
    public void pullUser(Model model,
                         @RequestParam("userName") String userName) {
        SeUser user = seUserService.selectByName(userName);
        if (user != null) {
            model.addAttribute("msg", "EXIST");
        } else {
            // ldap拉取用户实现
        }
    }
}
