package com.elichn.pub.core.model.bvo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SeRescBvo</p>
 * <p>Description: SeRescBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SeRescBvo {

    private Integer rescId;

    private String resString;

    private Integer fatherId;

    private String name;

    private String desc;

    private Integer showMenu;

    private List<SeRescBvo> list = new ArrayList<SeRescBvo>();

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getShowMenu() {
        return showMenu;
    }

    public void setShowMenu(Integer showMenu) {
        this.showMenu = showMenu;
    }

    public List<SeRescBvo> getList() {
        return list;
    }

    public void setList(List<SeRescBvo> list) {
        this.list = list;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRescId() {
        return rescId;
    }

    public void setRescId(Integer rescId) {
        this.rescId = rescId;
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }
}
