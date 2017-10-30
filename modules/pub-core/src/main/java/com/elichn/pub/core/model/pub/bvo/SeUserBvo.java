package com.elichn.pub.core.model.pub.bvo;

import com.elichn.pub.core.model.pub.pojo.security.SeUser;

/**
 * <p>Title: SeUserBvo</p>
 * <p>Description: SeUserBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SeUserBvo extends SeUser {

    private int userRole;

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
