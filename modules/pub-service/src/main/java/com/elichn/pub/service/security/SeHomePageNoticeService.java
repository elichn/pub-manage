package com.elichn.pub.service.security;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.model.pojo.security.SeHomePageNotice;

import java.util.List;

/**
 * <p>Title: SeHomePageNoticeService</p>
 * <p>Description: SeHomePageNoticeService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeHomePageNoticeService {

    /**
     * insert insert
     *
     * @param record  record
     * @param roleIds roleIds
     */
    void insert(SeHomePageNotice record, int[] roleIds);

    /**
     * updateByPrimaryKeyWithBLOBs updateByPrimaryKeyWithBLOBs
     *
     * @param record record
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(SeHomePageNotice record);

    /**
     * updateRoleNotice updateRoleNotice
     *
     * @param hnId    hnId
     * @param roleIds roleIds
     */
    void updateRoleNotice(int hnId, List<Integer> roleIds);

    /**
     * updateStatus updateStatus
     *
     * @param hnId   hnId
     * @param status status
     * @return int
     */
    int updateStatus(int hnId, int status);

    /**
     * getHomePageNoticeByUser getHomePageNoticeByUser
     *
     * @param userId userId
     * @return SeHomePageNotice
     */
    SeHomePageNotice getHomePageNoticeByUser(int userId);

    /**
     * getHomePageNoticeList getHomePageNoticeList
     *
     * @param qb qb
     * @return ResultBvo<SeHomePageNotice>
     */
    ResultBvo<SeHomePageNotice> getHomePageNoticeList(QueryBvo<SeHomePageNotice> qb);

    /**
     * getRelationRole getRelationRole
     *
     * @param hnId hnId
     * @return List<Integer>
     */
    List<Integer> getRelationRole(int hnId);

    /**
     * updateAsNew updateAsNew
     *
     * @param id id
     * @return int
     */
    int updateAsNew(int id);
}
