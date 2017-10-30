package com.elichn.pub.core.model.pub.pojo.security;

import java.io.Serializable;

public class SeUserRoleKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_user_role.user_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_user_role.role_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    private Integer roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table se_user_role
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_user_role.user_id
     *
     * @return the value of se_user_role.user_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_user_role.user_id
     *
     * @param userId the value for se_user_role.user_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_user_role.role_id
     *
     * @return the value of se_user_role.role_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_user_role.role_id
     *
     * @param roleId the value for se_user_role.role_id
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_user_role
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SeUserRoleKey other = (SeUserRoleKey) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_user_role
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_user_role
     *
     * @mbggenerated Sun Oct 29 22:40:33 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}