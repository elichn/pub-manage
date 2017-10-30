package com.elichn.pub.core.model.pub.bvo;


import com.elichn.pub.core.model.pub.pojo.security.SeResc;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: Menu</p>
 * <p>Description: Menu</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class Menu implements Serializable {

    private Integer id;

    private String name;

    private String url;

    private Integer priority;

    private List<Menu> subMenu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
    }

    public static Menu copy(SeResc r) {
        if (r == null) return null;
        else {
            Menu menu = new Menu();
            menu.setId(r.getId());
            menu.setName(r.getName());
            menu.setUrl(r.getShowUrl());
            menu.setPriority(r.getPriority());
            return menu;
        }
    }
}
