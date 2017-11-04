package com.elichn.pub.web.controller.security;

import com.elichn.pub.core.constant.CommonConstats;
import com.elichn.pub.core.model.bvo.Captcha;
import com.elichn.pub.core.model.bvo.Menu;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.core.util.DateTimeUtil;
import com.elichn.pub.service.pub.CaptchaService;
import com.elichn.pub.service.security.SeRoleRescService;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.controller.BaseController;
import com.elichn.pub.web.shiro.factorybean.CustomShiroFilterFactoryBean;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: SeLoginController</p>
 * <p>Description: SeLoginController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/")
public class SeLoginController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(SeLoginController.class);

    private final static String PREFIX = "/security/";

    @Autowired
    private SeRoleService seRoleService;
    @Autowired
    private SeUserService seUserService;
    @Autowired
    private SeRoleRescService seRoleRescService;
    @Autowired
    private CustomShiroFilterFactoryBean shiroFilter;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;

    private int maxTimes = 2;
    private int maxCheckTimes = 3;

    /**
     * @param model    model
     * @param request  request
     * @param response response
     * @param url      url
     * @return String
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "redirectUrl", required = false) String url) {
        model.addAttribute("misName", CommonConstats.MIS_NAME);
        model.addAttribute("redirectUrl", url);
        model.addAttribute("needCheckCode", this.getCheckCodeWrongTimes(request) >= maxTimes || this.getCheckCodeTimes(request) > maxCheckTimes);
        Cookie cookie = new Cookie("login_session_id", request.getSession().getId());
        cookie.setPath("/");
        cookie.setDomain(".yourwebsite.com");
        response.addCookie(cookie);
        return PREFIX + "login";
    }

    /**
     * getSessionId getSessionId
     *
     * @param request  request
     * @param response response
     * @throws IOException exception
     */
    @RequestMapping(value = "getSessionId", method = RequestMethod.GET)
    public void getSessionId(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        String callback = request.getParameter("callback");
        response.getWriter().write(callback + "('" + request.getSession().getId() + "')");
    }

    /**
     * byteArr2Int 由于都是10以内  所以不作过多处理
     *
     * @param arr arr
     * @return int
     */
    private int byteArr2Int(byte[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        } else {
            return Integer.valueOf(new String(arr));
        }
    }

    /**
     * index 首页入口
     *
     * @param model    model
     * @param request  request
     * @param response response
     * @param refer    refer
     * @return String
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "refer", required = false) String refer) {
        String userName = getUserName();
        if (StringUtils.isNotBlank(refer)) {
            Cookie cookie = new Cookie("refer", refer);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        if (StringUtils.isBlank(userName)) {
            return "redirect:/login";
        }
        model.addAttribute("userName", userName);
        model.addAttribute("misName", CommonConstats.MIS_NAME);
        // style session设置
        WebUtils.setSessionAttribute(request, CommonConstats.STYLE, CommonConstats.STYLE_VERTICALLY);
        return PREFIX + "index";
    }

    /**
     * index4horizontally 横版菜单首页入口
     *
     * @param model    model
     * @param request  request
     * @param response response
     * @param refer    refer
     * @return String
     */
    @RequestMapping(value = "index4horizontally", method = RequestMethod.GET)
    public String index4horizontally(Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "refer", required = false) String refer) {
        String userName = getUserName();
        if (StringUtils.isNotBlank(refer)) {
            Cookie cookie = new Cookie("refer", refer);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        if (StringUtils.isBlank(userName)) {
            return "redirect:/login";
        }
        model.addAttribute("menuList", this.getMenu());
        model.addAttribute("userName", userName);
        model.addAttribute("misName", CommonConstats.MIS_NAME);
        // style session设置
        WebUtils.setSessionAttribute(request, CommonConstats.STYLE, CommonConstats.STYLE_HORIZONTALLY);
        // 首页展示内容
        model.addAttribute("welcome", "/welcome");
        return PREFIX + "index4horizontally";
    }

    /**
     * checkCodes checkCodes
     *
     * @param request  request
     * @param response response
     */
    @RequestMapping(value = "checkCode", method = RequestMethod.GET)
    public void checkCodes(HttpServletRequest request, HttpServletResponse response) {
        clearCheckCodeTimes(request);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        Captcha captcha = captchaService.generateCaptcha();
        try {
            WebUtils.setSessionAttribute(request, CommonConstats.SESSION_CHECK_CODE, captcha.getCode().toLowerCase());
            response.getOutputStream().write(captcha.getJpegBytes());
        } catch (IOException e) {
            LOG.error("check code throw error,", e);
        }
    }

    /**
     * checkCodeVerify checkCodeVerify
     *
     * @param checkCode checkCode
     * @param request   request
     * @param response  response
     */
    @RequestMapping(value = "checkCodeVerify")
    public void checkCodeVerify(
            @RequestParam("checkCode") String checkCode,
            HttpServletRequest request,
            HttpServletResponse response) {
        String checkCodeReq = (String) WebUtils.getSessionAttribute(request, CommonConstats.SESSION_CHECK_CODE);
        if (checkCodeReq == null) {
            returnMsg(response, false);
        } else {
            int n = this.getCheckCodeTimes(request);
            boolean result = n <= maxCheckTimes && checkCodeReq.equalsIgnoreCase(checkCode.toLowerCase());
            returnMsg(response, result);
        }
        this.incrCheckCodeTimes(request);
    }

    /**
     * login 用户登录认证
     *
     * @param model   model
     * @param request request
     * @return String
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam(value = "checkCode", required = false) String userCheckCode,
            @RequestParam(value = "redirectUrl", required = false) String url,
            Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object o = WebUtils.getSessionAttribute(request, CommonConstats.SESSION_CHECK_CODE);
        String checkCode = o == null ? null : (String) o;
        int t = this.getCheckCodeTimes(request);
        if (t > maxCheckTimes) {
            model.addAttribute(ERROR_KEY, "验证码过期，请刷新验证码");
            return login(model, request, response, url);
        }
        if (StringUtils.isBlank(userName)) {
            model.addAttribute(ERROR_KEY, "用户名不能为空");
            return login(model, request, response, url);
        }
        if (StringUtils.isBlank(password)) {
            model.addAttribute(ERROR_KEY, "密码不能为空");
            return login(model, request, response, url);
        }
        boolean isNeedCheckCode = this.getCheckCodeWrongTimes(request) >= maxTimes;
        if (isNeedCheckCode) {
            if (userCheckCode != null && StringUtils.isNotBlank(checkCode)) {
                String inputCheckCode = userCheckCode.toLowerCase();
                int inputCheckCodeLength = 4;
                if (inputCheckCode.length() != inputCheckCodeLength || !inputCheckCode.equals(checkCode)) {
                    model.addAttribute(ERROR_KEY, "验证码错误");
                    return login(model, request, response, url);
                }
            } else {
                model.addAttribute(ERROR_KEY, "验证码错误");
                return login(model, request, response, url);
            }
        }
        Subject currentUser = SecurityUtils.getSubject();

        String remoteAddr = getRemoteAddr(request);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, remoteAddr);
        /** token.setRememberMe(true);*/
        try {
            currentUser.login(token);
            SeUser user = seUserService.selectByName(getUserName());
            writeLog(request, "用户登录", null, user.getUsername());
            if (StringUtils.isNotBlank(url)) {
                response.sendRedirect(url);
            } else {
                response.sendRedirect(request.getContextPath() + "/index");
            }
            this.resetCheckCodeWrongTimes(request);
            this.clearCheckCodeTimes(request);
            return null;
        } catch (AuthenticationException ae) {
            model.addAttribute(ERROR_KEY, "用户名或密码错误");
            writeLog(request, "登录失败", "试图使用用户名:" + userName + "登录");
            LOG.error("登录失败，用户名或密码错误", ae);
            this.incrCheckCodeWrongTimes(request);
            this.incrCheckCodeTimes(request);
            return login(model, request, response, url);
        }
    }

    /**
     * getMenuList 获取菜单数据
     *
     * @param model model
     */
    @RequestMapping(value = "menuList")
    public void getMenuList(Model model) {
        model.addAttribute("list", getRescOfUser());
    }

    /**
     * accessDenied 用户没有访问权限所跳转的页面
     *
     * @return String
     */
    @RequestMapping(value = "accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        model.addAttribute("message", "无权限");
        return PREFIX + "accessDenied";
    }

    /**
     * logout 用户登出
     *
     * @param model   logout
     * @param request request
     * @return String
     */
    @RequestMapping(value = "logout")
    public String logout(Model model, HttpServletRequest request) {
        String userName = getUserName();
        LOG.info("用户={}退出登录", userName);
        if (userName != null) {
            SecurityUtils.getSubject().logout();
            writeLog(request, "用户注销", null, userName);
        }
        return "redirect:/login";
    }

    /**
     * islogin 用户是否登录
     *
     * @param model model
     */
    @RequestMapping(value = "islogin", method = RequestMethod.GET)
    public void islogin(Model model) {
        String userName = getUserName();
        if (StringUtils.isNotBlank(userName)) {
            model.addAttribute("flag", true);
        } else {
            model.addAttribute("flag", false);
        }
    }

    /**
     * refreshPublicPermission refreshPublicPermission
     *
     * @param model model
     */
    @RequestMapping(value = "refreshPublicPermission", method = RequestMethod.GET)
    public void refreshPublicPermission(Model model) {
        shiroFilter.refreshFilter();
        model.addAttribute("status", SUCCESS);
    }

    /**
     * welcome welcome
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String welcome(Model model) {
        return PREFIX + "welcome";
    }

    /**
     * ping ping
     *
     * @param response response
     */
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public void ping(HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("pong");
        } catch (Exception e) {
            LOG.error("ping 异常,", e);
        }
    }

    /**
     * getRescOfUser 获取用户具有的资源
     *
     * @return List<SeResc>
     */
    private List<SeResc> getRescOfUser() {
        // 获得当前用户
        SeUser currentUser = getCurrentUser();
        // 根据当前用户活动当前用户的所有角色
        List<SeRole> currentRoles = seRoleService.selectRoleListByUser(currentUser.getId().toString());
        // 获取角色具有的所有资源
        List<SeResc> rescs = new ArrayList<SeResc>();
        for (SeRole r : currentRoles) {
            List<SeResc> rescList = seRoleRescService.getRescByRole(r.getId());
            rescs.addAll(rescList);
        }

        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>(16);
        List<SeResc> list = new ArrayList<SeResc>();
        for (SeResc resc : rescs) {
            if (map.get(resc.getId()) == null && resc.getShowMenu() != 0) {
                list.add(resc);
                map.put(resc.getId(), true);
            }
        }

        Collections.sort(list, new Comparator<SeResc>() {
            @Override
            public int compare(SeResc o1, SeResc o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        return list;
    }


    /**
     * getMenu 获取横版菜单
     *
     * @return List<Menu>
     */
    private List<Menu> getMenu() {
        // 获得当前用户
        SeUser currentUser = getCurrentUser();
        // 根据当前用户活动当前用户的所有角色
        List<SeRole> currentRoles = seRoleService.selectRoleListByUser(currentUser.getId().toString());
        // 获取角色具有的所有资源
        List<SeResc> rescs = new ArrayList<SeResc>();
        for (SeRole r : currentRoles) {
            List<SeResc> rescList = seRoleRescService.getRescByRole(r.getId());
            rescs.addAll(rescList);
        }
        List<Menu> menus = new ArrayList<>();
        Map<Integer, List<Menu>> menuMap = new HashMap<>(128);
        Set<Integer> set = new HashSet<>();
        for (SeResc resc : rescs) {
            if (!set.contains(resc.getId()) && resc.getShowMenu() != 0) {
                if (resc.getFatherId() == 0) {
                    menus.add(Menu.copy(resc));
                } else {
                    List<Menu> menuList = menuMap.get(resc.getFatherId());
                    if (menuList == null) {
                        menuList = new ArrayList<>();
                        menuMap.put(resc.getFatherId(), menuList);
                    }
                    menuList.add(Menu.copy(resc));
                }
                set.add(resc.getId());
            }
        }
        // 一级菜单设置子菜单
        for (Menu m : menus) {
            this.setSubMenu(menuMap, m);
        }
        // 二级菜单设置子菜单
        for (Menu m : menus) {
            List<Menu> subMenu = m.getSubMenu();
            if (subMenu == null || subMenu.isEmpty()) {
                continue;
            }
            for (Menu sub : subMenu) {
                this.setSubMenu(menuMap, sub);
            }

        }
        // 三级菜单设置子菜单
        for (Menu m : menus) {
            List<Menu> subMenu = m.getSubMenu();
            if (subMenu == null || subMenu.isEmpty()) {
                continue;
            }
            for (Menu thirdSub : subMenu) {
                List<Menu> thirdMenu = thirdSub.getSubMenu();
                if (thirdMenu == null || thirdMenu.isEmpty()) {
                    continue;
                }
                for (Menu sub : thirdMenu) {
                    this.setSubMenu(menuMap, sub);
                }
            }

        }
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        return menus;
    }

    /**
     * setSubMenu 设置子菜单列表
     *
     * @param menuMap menuMap
     * @param menu    菜单
     */
    private void setSubMenu(Map<Integer, List<Menu>> menuMap, Menu menu) {
        List<Menu> menuList = menuMap.get(menu.getId());
        if (menuList != null) {
            Collections.sort(menuList, new Comparator<Menu>() {
                @Override
                public int compare(Menu o1, Menu o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });
            menu.setSubMenu(menuList);
        }
    }

    /**
     * incrCheckCodeWrongTimes incrCheckCodeWrongTimes
     *
     * @param request request
     */
    private void incrCheckCodeWrongTimes(HttpServletRequest request) {
        String today = DateTime.now().toString(DateTimeUtil.YMD);
        BoundValueOperations<String, byte[]> v = redisTemplate.boundValueOps(today + "_" + getRemoteAddr(request));
        v.increment(1);
        v.expireAt(DateTime.now().plusDays(1).withHourOfDay(0).withMinuteOfHour(0)
                .withSecondOfMinute(0).withMillisOfSecond(0).toDate());
        // System.out.println(byteArr2Int(v.get()));
    }

    /**
     * resetCheckCodeWrongTimes resetCheckCodeWrongTimes
     *
     * @param request request
     */
    private void resetCheckCodeWrongTimes(HttpServletRequest request) {
        String today = DateTime.now().toString(DateTimeUtil.YMD);
        BoundValueOperations<String, byte[]> v = redisTemplate.boundValueOps(today + "_" + getRemoteAddr(request));
        v.expire(0, TimeUnit.MILLISECONDS);
    }

    /**
     * getCheckCodeWrongTimes getCheckCodeWrongTimes
     *
     * @param request request
     * @return int
     */
    private int getCheckCodeWrongTimes(HttpServletRequest request) {
        String today = DateTime.now().toString(DateTimeUtil.YMD);
        BoundValueOperations<String, byte[]> v = redisTemplate.boundValueOps(today + "_" + getRemoteAddr(request));
        return byteArr2Int(v.get());
    }

    /**
     * incrCheckCodeTimes incrCheckCodeTimes
     *
     * @param request request
     */
    private void incrCheckCodeTimes(HttpServletRequest request) {
        int times = this.getCheckCodeTimes(request);
        WebUtils.setSessionAttribute(request, "CHECK_TIMES", times + 1);
    }

    /**
     * getCheckCodeTimes getCheckCodeTimes
     *
     * @param request request
     * @return int
     */
    private int getCheckCodeTimes(HttpServletRequest request) {
        Object times = WebUtils.getSessionAttribute(request, "CHECK_TIMES");
        return times == null ? 0 : (Integer) times;
    }

    /**
     * clearCheckCodeTimes
     *
     * @param request request
     */
    private void clearCheckCodeTimes(HttpServletRequest request) {
        WebUtils.setSessionAttribute(request, "CHECK_TIMES", null);
    }
}
