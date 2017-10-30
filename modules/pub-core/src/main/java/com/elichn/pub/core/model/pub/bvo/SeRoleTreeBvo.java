package com.elichn.pub.core.model.pub.bvo;

import com.elichn.pub.core.model.pub.pojo.security.SeRole;

/**
 * <p>Title: SeRoleTreeBvo</p>
 * <p>Description: SeRoleTreeBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SeRoleTreeBvo extends SeRole {

    private boolean checked;

    private boolean nocheck;

    public static SeRoleTreeBvo copyFromRole(SeRole role) {
        SeRoleTreeBvo bvo = new SeRoleTreeBvo();
        bvo.setId(role.getId());
        bvo.setParentId(role.getParentId());
        bvo.setRoleName(role.getRoleName());
        return bvo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }
}
