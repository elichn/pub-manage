package com.elichn.pub.core.model.bvo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: ResultBvo</p>
 * <p>Description: ResultBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class ResultBvo<T> implements Serializable {

    private int total;

    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
