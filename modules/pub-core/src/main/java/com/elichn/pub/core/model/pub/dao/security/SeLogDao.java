package com.elichn.pub.core.model.pub.dao.security;

import com.elichn.pub.core.model.pub.bvo.QueryBvo;
import com.elichn.pub.core.model.pub.pojo.security.SeLog;

import java.util.List;

/**
 * <p>Title: SeLogDao</p>
 * <p>Description: SeLogDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeLogDao {

    /**
     * getList getList
     *
     * @param queryBvo queryBvo
     * @return List<SeLog>
     */
    List<SeLog> getList(QueryBvo<SeLog> queryBvo);

    /**
     * getListCount getListCount
     *
     * @param queryBvo queryBvo
     * @return int
     */
    int getListCount(QueryBvo<SeLog> queryBvo);
}
