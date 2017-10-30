package com.elichn.pub.core.model.pub.dao.security;

import com.elichn.pub.core.model.pub.bvo.QueryBvo;
import com.elichn.pub.core.model.pub.pojo.security.SeHomePageNotice;

import java.util.List;

/**
 * <p>Title: SeHomePageNoticeDao</p>
 * <p>Description: SeHomePageNoticeDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeHomePageNoticeDao {

    /**
     * getHomePageNoticeByRole getHomePageNoticeByRole
     *
     * @param roleId roleId
     * @return SeHomePageNotice
     */
    SeHomePageNotice getHomePageNoticeByRole(int roleId);

    /**
     * getRelationRole getRelationRole
     *
     * @param hnId hnId
     * @return List<Integer>
     */
    List<Integer> getRelationRole(int hnId);

    /**
     * getHomePageNoticeList getHomePageNoticeList
     *
     * @param qb qb
     * @return List<SeHomePageNotice>
     */
    List<SeHomePageNotice> getHomePageNoticeList(QueryBvo<SeHomePageNotice> qb);

    /**
     * getHomePageNoticeListCount getHomePageNoticeListCount
     *
     * @param qb qb
     * @return int
     */
    int getHomePageNoticeListCount(QueryBvo<SeHomePageNotice> qb);
}
