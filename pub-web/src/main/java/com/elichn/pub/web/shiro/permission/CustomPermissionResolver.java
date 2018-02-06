package com.elichn.pub.web.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * <p>Title: CustomPermissionResolver</p>
 * <p>Description: CustomPermissionResolver</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class CustomPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String s) {
        if (s.startsWith(RegexPermission.PREFIX)) {
            return new RegexPermission(s.substring(RegexPermission.PREFIX.length()));
        } else {
            return new WildcardPermission(s);
        }
    }
}
