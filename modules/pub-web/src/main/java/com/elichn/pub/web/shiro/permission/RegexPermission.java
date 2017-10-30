package com.elichn.pub.web.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * <p>Title: RegexPermission</p>
 * <p>Description: RegexPermission</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class RegexPermission implements Permission, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexPermission.class);

    public static final String PREFIX = "regex:";

    private String regexString;

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof RegexPermission)) {
            return false;
        }
        try {
            RegexPermission rp = (RegexPermission) p;
            return rp.regexString.matches(regexString);
        } catch (Exception e) {
            LOGGER.info("error:{}", e);
            return false;
        }
    }

    public RegexPermission(String regexString) {
        this.regexString = regexString;
    }
}
