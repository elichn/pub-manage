package com.elichn.pub.core.model.pojo.security;

import java.io.Serializable;

public class SeResc implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.name
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.show_url
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private String showUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.res_string
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private String resString;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.priority
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private Integer priority;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.descn
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private String descn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.father_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private Integer fatherId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.show_menu
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private Integer showMenu;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column se_resc.module_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private Integer moduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table se_resc
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.id
     *
     * @return the value of se_resc.id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.id
     *
     * @param id the value for se_resc.id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.name
     *
     * @return the value of se_resc.name
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.name
     *
     * @param name the value for se_resc.name
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.show_url
     *
     * @return the value of se_resc.show_url
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public String getShowUrl() {
        return showUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.show_url
     *
     * @param showUrl the value for se_resc.show_url
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl == null ? null : showUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.res_string
     *
     * @return the value of se_resc.res_string
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public String getResString() {
        return resString;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.res_string
     *
     * @param resString the value for se_resc.res_string
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setResString(String resString) {
        this.resString = resString == null ? null : resString.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.priority
     *
     * @return the value of se_resc.priority
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.priority
     *
     * @param priority the value for se_resc.priority
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.descn
     *
     * @return the value of se_resc.descn
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public String getDescn() {
        return descn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.descn
     *
     * @param descn the value for se_resc.descn
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setDescn(String descn) {
        this.descn = descn == null ? null : descn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.father_id
     *
     * @return the value of se_resc.father_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public Integer getFatherId() {
        return fatherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.father_id
     *
     * @param fatherId the value for se_resc.father_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.show_menu
     *
     * @return the value of se_resc.show_menu
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public Integer getShowMenu() {
        return showMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.show_menu
     *
     * @param showMenu the value for se_resc.show_menu
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setShowMenu(Integer showMenu) {
        this.showMenu = showMenu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column se_resc.module_id
     *
     * @return the value of se_resc.module_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column se_resc.module_id
     *
     * @param moduleId the value for se_resc.module_id
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_resc
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
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
        SeResc other = (SeResc) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getShowUrl() == null ? other.getShowUrl() == null : this.getShowUrl().equals(other.getShowUrl()))
            && (this.getResString() == null ? other.getResString() == null : this.getResString().equals(other.getResString()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getDescn() == null ? other.getDescn() == null : this.getDescn().equals(other.getDescn()))
            && (this.getFatherId() == null ? other.getFatherId() == null : this.getFatherId().equals(other.getFatherId()))
            && (this.getShowMenu() == null ? other.getShowMenu() == null : this.getShowMenu().equals(other.getShowMenu()))
            && (this.getModuleId() == null ? other.getModuleId() == null : this.getModuleId().equals(other.getModuleId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_resc
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getShowUrl() == null) ? 0 : getShowUrl().hashCode());
        result = prime * result + ((getResString() == null) ? 0 : getResString().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getDescn() == null) ? 0 : getDescn().hashCode());
        result = prime * result + ((getFatherId() == null) ? 0 : getFatherId().hashCode());
        result = prime * result + ((getShowMenu() == null) ? 0 : getShowMenu().hashCode());
        result = prime * result + ((getModuleId() == null) ? 0 : getModuleId().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table se_resc
     *
     * @mbggenerated Sun Oct 29 22:39:56 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", showUrl=").append(showUrl);
        sb.append(", resString=").append(resString);
        sb.append(", priority=").append(priority);
        sb.append(", descn=").append(descn);
        sb.append(", fatherId=").append(fatherId);
        sb.append(", showMenu=").append(showMenu);
        sb.append(", moduleId=").append(moduleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}