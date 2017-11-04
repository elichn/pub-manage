package com.elichn.pub.web.controller.security;


import com.elichn.pub.core.model.bvo.SeRescTreeBvo;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRescRoleKey;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.service.security.SeRoleRescService;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.controller.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * <p>Title: SeRoleController</p>
 * <p>Description: SeRoleController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/role")
public class SeRoleController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(SeRoleController.class);

    private final static String PREFIX = "/security/role";

    @Autowired
    private SeRoleService seRoleService;
    @Autowired
    private SeUserService seUserService;
    @Autowired
    private SeRoleRescService seRoleRescService;

    /**
     * roleManager 角色管理入口页
     *
     * @param model
     * @return model
     */
    @RequestMapping(value = "/roleManager", method = RequestMethod.GET)
    public String roleManager(Model model) {
        return PREFIX + "/roleManager";
    }

    /**
     * isRole 判断权限是否存在
     *
     * @param roleName roleName
     */
    @RequestMapping(value = "/isRole", method = RequestMethod.GET)
    public void isRole(@RequestParam("roleName") String roleName,
                       @RequestParam("id") Integer id,
                       HttpServletResponse response) {
        try {
            roleName = URLDecoder.decode(roleName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOG.info("{}", e);
        }
        Boolean isRole = seRoleService.isRole(roleName, id);
        returnMsg(response, !isRole);
    }

    /**
     * getRoleTree 查询数据权限
     *
     * @param model model
     */
    @RequestMapping(value = "/getRoleTree")
    public void getRoleTree(Model model) {
        String userName = getUserName();
        if (userName != null) {
            SeUser user = seUserService.selectByName(userName);
            if (user != null) {
                List<SeRole> list = seRoleService.selectRolesByUser(user.getId());
                model.addAttribute("list", list);
            }
        }
    }

    /**
     * getRescByRole 查询数据权限
     * 如果是角色是超级管理员  则直接加载所有权限
     * 如果是当前用户拥有的直接角色 则不能去查找父角色的权限
     * 如果是间接角色 则需要加载父用户的权限
     *
     * @param model model
     * @param id    id
     */
    @RequestMapping(value = "/getRescByRole")
    public void getRescByRole(Model model,
                              @RequestParam(value = "id") Integer id) {
        boolean isDirect;
        try {
            isDirect = this.isDirectRoleOfUser(id);
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            LOG.info("{}", e);
            return;
        }
        // 获取当前role
        SeRole role = seRoleService.selectRoleById(id);
        if (role != null) {
            List<SeRescTreeBvo> bvos = new ArrayList<SeRescTreeBvo>();
            // 超级管理员
            if (role.getParentId() == 0) {
                List<SeResc> list = seRoleRescService.selectRescs();
                for (SeResc resc : list) {
                    SeRescTreeBvo bvo = SeRescTreeBvo.copyFromResc(resc);
                    bvo.setChecked(true);
                    bvos.add(bvo);
                }
            } else {
                if (isDirect) {
                    List<SeResc> list = seRoleRescService.getRescByRole(id);

                    for (SeResc resc : list) {
                        SeRescTreeBvo bvo = SeRescTreeBvo.copyFromResc(resc);
                        bvo.setChecked(false);
                        bvos.add(bvo);
                    }
                } else {
                    // 如果其父角色是超级管理员 那么他可选择所有的权限
                    SeRole parentRole = seRoleService.selectRoleById(role.getParentId());
                    List<SeResc> list;
                    if (parentRole != null && parentRole.getParentId() == 0) {
                        list = seRoleRescService.selectRescs();
                    } else {
                        list = seRoleRescService.getRescByRole(role.getParentId());
                    }
                    List<Integer> rescs = new ArrayList<Integer>();

                    // 获取当前角色的资源
                    if (id != null && id > 0) {
                        List<SeRescRoleKey> keys = seRoleRescService.selectRescRoleKeyByRoleId(id);
                        for (SeRescRoleKey key : keys) {
                            rescs.add(key.getRescId());
                        }
                    }

                    for (SeResc resc : list) {
                        SeRescTreeBvo bvo = SeRescTreeBvo.copyFromResc(resc);
                        if (rescs.contains(resc.getId())) {
                            bvo.setChecked(true);
                        } else {
                            bvo.setChecked(false);
                        }
                        bvos.add(bvo);
                    }
                }
            }

            Collections.sort(bvos, new Comparator<SeResc>() {
                @Override
                public int compare(SeResc o1, SeResc o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });

            model.addAttribute("isDirect", isDirect);
            model.addAttribute("list", bvos);
        }
    }

    /**
     * isRoleExist 验证role表中code是否存在
     *
     * @param response response
     * @param code     code
     * @param id       id
     */
    @RequestMapping(value = "/isRoleExist", method = RequestMethod.GET)
    public void isRoleExist(HttpServletResponse response,
                            @RequestParam(value = "code", required = false) String code,
                            @RequestParam(value = "id", required = false) Integer id) {
        try {
            code = URLDecoder.decode(code, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SeRole role = seRoleService.selectRoleByCode(code);
        Boolean isRoleExist = false;
        if (role != null && !role.getId().equals(id)) {
            isRoleExist = true;
        }
        returnMsg(response, !isRoleExist);
    }

    /**
     * addRole 添加角色
     *
     * @param model    model
     * @param request  request
     * @param roleName roleName
     * @param pid      pid
     * @param code     code
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public void addRole(Model model,
                        HttpServletRequest request,
                        @RequestParam("roleName") String roleName,
                        @RequestParam("pid") Integer pid,
                        @RequestParam("code") String code) {
        SeRole role = new SeRole();
        role.setRoleName(roleName);
        role.setParentId(pid);
        if (StringUtils.isNotBlank(code)) {
            role.setCode(code);
        }
        SeRole cr = seRoleService.insertRole(role);
        model.addAttribute("role", cr);
        writeLog(request, "添加角色", "添加角色(" + roleName + ")");
    }

    /**
     * updateRoleResc 更新角色资源
     */
    @RequestMapping(value = "/updateRoleResc", method = RequestMethod.POST)
    public void updateRoleResc(
            Model model,
            HttpServletRequest request,
            @RequestParam("roleId") Integer roleId,
            @RequestParam("list") String list) throws Exception {
        // 不能修改直接拥有的权限
        boolean isDirect = this.isDirectRoleOfUser(roleId);
        if (isDirect) {
            model.addAttribute("msg", FAIL);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Map> value = mapper.readValue(list, ArrayList.class);
        for (Map m : value) {
            int rescId = (Integer) m.get("id");
            boolean checked = (Boolean) m.get("checked");

            SeRescRoleKey key = new SeRescRoleKey();
            key.setRoleId(roleId);
            key.setRescId(rescId);
            if (checked) {
                seRoleRescService.insertRescRole(key);
            } else {
                seRoleRescService.deleteByPrimaryKey(key);
            }
        }

        model.addAttribute("msg", SUCCESS);
    }

    /**
     * updateRole 更新角色
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public void updateRole(
            Model model,
            @RequestParam("id") Integer roleId,
            @RequestParam("roleName") String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "tags", required = false) int[] tags) throws Exception {
        // 不能重命名直接拥有的权限
        boolean isDirect = this.isDirectRoleOfUser(roleId);
        if (isDirect) {
            model.addAttribute("msg", FAIL);
            return;
        }
        if (StringUtils.isNotBlank(name)) {
            SeRole role = seRoleService.selectRoleById(roleId);
            role.setRoleName(name.trim());
            if (StringUtils.isNotBlank(code)) {
                role.setCode(code);
            }
            seRoleService.updateRole(role);
            model.addAttribute("msg", SUCCESS);
            model.addAttribute("currentRole", role);
        } else {
            model.addAttribute("msg", INVALID_PARAM);
        }
    }

    /**
     * deleteRole 删除角色
     *
     * @param model   model
     * @param request request
     * @param roleId  roleId
     */
    @RequestMapping(value = "/deleteRole")
    public void deleteRole(Model model,
                           HttpServletRequest request,
                           @RequestParam("roleId") Integer roleId) throws Exception {
        // 不能删除直接拥有的权限
        boolean isDirect = this.isDirectRoleOfUser(roleId);
        if (isDirect) {
            model.addAttribute("msg", FAIL);
            return;
        }
        this.deleteRoleRecursive(request, roleId);
        model.addAttribute("msg", SUCCESS);
    }

    /**
     * deleteRoleRecursive 递归地删除角色
     *
     * @param request request
     * @param roleId  roleId
     */
    private void deleteRoleRecursive(HttpServletRequest request, Integer roleId) {
        List<SeRole> roles = seRoleService.selectByPid(roleId);
        for (SeRole role : roles) {
            deleteRoleRecursive(request, role.getId());
        }
        seRoleService.deleteRoleById(roleId);
        seRoleRescService.deleteRescRoleByRoleId(roleId);
        seUserService.deleteUserRoleByRoleId(roleId);
        writeLog(request, "删除角色", "删除角色(id:" + roleId + ")");
    }

    /**
     * isDirectRoleOfUser 是否是用户直接拥有的角色
     *
     * @param roleId roleId
     * @return boolean
     * @throws Exception exception
     */
    private boolean isDirectRoleOfUser(long roleId) throws Exception {
        String userName = getUserName();
        SeUser user = seUserService.selectByName(userName);
        if (user == null) {
            throw new Exception(USER_NOT_EXIST);
        }
        List<SeRole> roleList = seRoleService.selectRoleListByUser(user.getId().toString());
        for (SeRole r : roleList) {
            if (r.getId().longValue() == roleId) {
                return true;
            }
        }
        return false;
    }
}
