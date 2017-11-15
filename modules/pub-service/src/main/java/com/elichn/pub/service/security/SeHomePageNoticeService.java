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
     * insert 首页通知插入
     *
     * @param record  record
     * @param roleIds roleIds
     */
    void insert(SeHomePageNotice record, int[] roleIds);

    /**
     * updateByPrimaryKeyWithBLOBs 根据主键更新首页通知
     *
     * @param record record
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(SeHomePageNotice record);

    /**
     * updateRoleNotice 更新角色通知
     *
     * @param hnId    hnId
     * @param roleIds roleIds
     */
    void updateRoleNotice(Integer hnId, List<Integer> roleIds);

    /**
     * updateStatus 更新状态为status (发布/不发布)
     *
     * @param hnId   hnId
     * @param status status
     * @return int
     */
    int updateStatus(Integer hnId, Integer status);

    /**
     * selectHomePageNoticeByUserId 通过userId查询该角色的首页通知
     *
     * @param userId userId
     * @return SeHomePageNotice
     */
    SeHomePageNotice selectHomePageNoticeByUserId(Integer userId);

    /**
     * selectHomePageNoticeList4Page 根据查询条件分页查询首页通知列表
     *
     * @param qb qb
     * @return ResultBvo<SeHomePageNotice>
     */
    ResultBvo<SeHomePageNotice> selectHomePageNoticeList4Page(QueryBvo<SeHomePageNotice> qb);

    /**
     * selectRelationRoleList 通过首页通知id查询角色id列表
     *
     * @param hnId hnId
     * @return List<Integer>
     */
    List<Integer> selectRelationRoleList(Integer hnId);

    /**
     * updateAsNew 设为最新通知
     *
     * @param id id
     * @return int
     */
    int updateAsNew(Integer id);
}
