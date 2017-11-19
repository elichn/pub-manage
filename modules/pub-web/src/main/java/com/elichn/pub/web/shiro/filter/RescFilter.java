package com.elichn.pub.web.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>Title: RescFilter</p>
 * <p>Description: RescFilter</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class RescFilter extends AuthorizationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RescFilter.class);
    /**
     * 忽略的url
     */
    private List<String> ignoreList;
    /**
     * 忽略 指定前缀
     */
    private List<String> ignoreHeadList;
    private String jsonName = ".json";

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        String path = getPathWithinApplication(request);
        // 去除.json的后缀
        if (path.endsWith(jsonName)) {
            path = path.substring(0, path.length() - 5);
        }
        // 忽略（通过）特定后缀的访问
        String ext = getExt(path);
        if (ext != null && !jsonName.equals(ext)) {
            return true;
        }
        if (ignoreHeadList != null) {
            for (String s : ignoreHeadList) {
                if (path.startsWith(s)) {
                    return true;
                }
            }
        }
        // 忽略（通过）的url
        if (ignoreList != null && ignoreList.contains(path)) {
            return true;
        }
        // url改写 如/site/add  改为/site:add，就是把后面的操作（方法）区分出来
        int i = path.lastIndexOf('/');
        if (i > 0) {
            path = path.substring(0, i) + ":" + path.substring(i + 1, path.length());
        } else if (i == 0) {
            path = "/:" + path.substring(1, path.length());
        }
        // 进行权限验证
        Subject subject = getSubject(request, response);
        boolean isPermitted = subject.isPermitted(path);
        LOG.info(path + ":" + isPermitted);
        return isPermitted;
    }

    /**
     * getExt 获取后缀  .js .css等
     *
     * @param path path
     * @return String
     */
    private String getExt(String path) {
        if (path != null) {
            int index = path.lastIndexOf(".");
            if (index >= 0) {
                return path.substring(index, path.length());
            }
        }
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            String path = getPathWithinApplication(request);
            Cookie cookie = new Cookie("refer", path);
            cookie.setPath("/");
            /** cookie.setDomain(".yourwebsite.com");*/
            ((HttpServletResponse) response).addCookie(cookie);
            saveRequestAndRedirectToLogin(request, response);
        } else {
            /**
             *  If subject is known but not authorized, redirect to the unauthorized URL if there is one
             *  If no unauthorized URL is specified, just return an unauthorized HTTP status code
             */
            String unauthorizedUrl = getUnauthorizedUrl();
            String path = getPathWithinApplication(request);
            // 如果以.json的形式访问 则返回.json形式的提醒
            if (path.endsWith(jsonName)) {
                unauthorizedUrl += jsonName;
            }
            // SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

    public void setIgnoreHeadList(List<String> ignoreHeadList) {
        this.ignoreHeadList = ignoreHeadList;
    }

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }

}
