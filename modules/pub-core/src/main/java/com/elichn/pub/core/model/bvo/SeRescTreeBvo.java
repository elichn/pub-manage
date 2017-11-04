package com.elichn.pub.core.model.bvo;

import com.elichn.pub.core.model.pojo.security.SeResc;

/**
 * <p>Title: SeRescTreeBvo</p>
 * <p>Description: SeRescTreeBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SeRescTreeBvo extends SeResc {

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static SeRescTreeBvo copyFromResc(SeResc resc) {
        SeRescTreeBvo bvo = new SeRescTreeBvo();
        bvo.setId(resc.getId());
        bvo.setName(resc.getName());
        bvo.setDescn(resc.getDescn());
        bvo.setFatherId(resc.getFatherId());
        bvo.setPriority(resc.getPriority());
        bvo.setResString(resc.getResString());
        bvo.setShowUrl(resc.getShowUrl());
        bvo.setShowMenu(resc.getShowMenu());
        return bvo;
    }
}
