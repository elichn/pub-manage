package com.elichn.pub.web.shiro.realm;

import com.elichn.pub.core.constant.UserTypeEnum;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.service.security.SeRoleRescService;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.shiro.cache.RedisCacheManager;
import com.elichn.pub.web.shiro.util.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <p>Title: CustomJdbcRealm</p>
 * <p>Description: CustomJdbcRealm</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class CustomJdbcRealm extends JdbcRealm {

    private static final Logger LOG = LoggerFactory.getLogger(CustomJdbcRealm.class);

    @Autowired
    private SeRoleService seRoleService;

    @Autowired
    private SeUserService seUserService;

    @Autowired
    private SeRoleRescService seRoleRescService;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userName = upToken.getUsername();
        if (userName == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        // 暂定都是标准用户,密码都保存在db中
        String password = getDbPassword(userName);
        // password = getPassWordByLdap(upToken);  // ldap验证口
        if (StringUtils.isBlank(password)) {
            throw new UnknownAccountException("No account found for user [" + userName + "]");
        }

        return new SimpleAuthenticationInfo(userName, password.toCharArray(), getName());
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) getAvailablePrincipal(principals);
        SeUser user = seUserService.selectByName(userName);
        Set<String> permissions = new HashSet<String>();
        Set<String> roleNames = new HashSet<String>();
        if (user != null) {
            List<SeRole> roleList = seRoleService.selectRoleListByUserId(user.getId());
            List<Integer> roleIds = new ArrayList<Integer>();
            for (SeRole r : roleList) {
                roleNames.add(r.getId().toString());
                roleIds.add(r.getId());
            }
            if (permissionsLookupEnabled && roleIds.size() > 0) {
                List<SeResc> rescList = seRoleRescService.selectRescListByRoleIds(roleIds);
                for (SeResc resc : rescList) {
                    if (StringUtils.isNotBlank(resc.getResString())) {
                        String[] rescArr = resc.getResString().split(";");
                        permissions.addAll(Arrays.asList(rescArr));
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }
        AuthorizationInfo info = null;
        if (LOG.isTraceEnabled()) {
            LOG.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
        }
        Cache<Object, byte[]> cache = redisCacheManager.getCache((String) principals.getPrimaryPrincipal());
        if (cache != null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempting to retrieve the AuthorizationInfo from cache.");
            }
            info = (AuthorizationInfo) SerializeUtil.deserialize(cache.get(principals.getPrimaryPrincipal()));
            if (LOG.isTraceEnabled()) {
                if (info == null) {
                    LOG.trace("No AuthorizationInfo found in cache for principals [" + principals + "]");
                } else {
                    LOG.trace("AuthorizationInfo found in cache for principals [" + principals + "]");
                }
            }
        }
        if (info == null) {
            // Call template method if the info was not found in a cache
            info = doGetAuthorizationInfo(principals);
            // If the info is not null and the cache has been created, then cache the authorization info.
            if (info != null && cache != null) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Caching authorization info for principals: [" + principals + "].");
                }
                cache.put(principals.getPrimaryPrincipal(), SerializeUtil.serialize(info));
            }
        }
        return info;
    }

    /**
     * getDbPassword getDbPassword
     *
     * @param userName userName
     * @return String
     */
    private String getDbPassword(String userName) {
        SeUser user = seUserService.selectByName(userName);
        if (user != null && user.getUserType() == UserTypeEnum.STANDARD.getTypeValue()) {
            if (user.getStatus() == 1) {
                return user.getPassword();
            } else {
                throw new LockedAccountException();
            }
        } else {
            return null;
        }
    }

/*    public String getPassWordByLdap(AuthenticationToken token) {
        // loap getPassWordByLdap 实现
        return null;
    }*/
}
