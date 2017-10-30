package com.elichn.pub.core.model.pub.bvo;

/**
 * <p>Title: SeUserRoleBvo</p>
 * <p>Description: SeUserRoleBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SeUserRoleBvo {

    private String userId;

    private String roleId;

    private String userName;

    private String cnName;

    private Integer userType;

    private String passWord;

    private String userDescn;

    private String status;

    private String roleName;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserDescn() {
        return userDescn;
    }

    public void setUserDescn(String userDescn) {
        this.userDescn = userDescn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
