package com.elichn.pub.core.model.bvo;

import java.io.Serializable;

/**
 * <p>Title: QueryBvo</p>
 * <p>Description: QueryBvo</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class QueryBvo<T> implements Serializable {

    private T queryBean;

    private int pageNo = 1;

    private int pageSize = 10;

    private int offset;

    private int rows;

    private boolean paging = true;

    private String orderBy = null;

    private String sortType = "desc";

    public QueryBvo() {
    }


    public QueryBvo(T queryBean) {
        this.queryBean = queryBean;
        this.paging = false;
    }

    public QueryBvo(T queryBean, int pageNo, int pageSize) {
        this.queryBean = queryBean;
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public T getQueryBean() {
        return queryBean;
    }

    public void setQueryBean(T queryBean) {
        this.queryBean = queryBean;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo > 0 ? pageNo : 1;
        this.offset = (pageNo - 1) * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : 10;
        this.rows = pageSize;
        this.offset = (pageNo - 1) * pageSize;
    }

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
