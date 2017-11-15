package com.elichn.pub.core.dao.security;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.pojo.security.SeHomePageNotice;

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
     * selectHomePageNoticeByRole 通过roleId获取该角色的首页通知
     *
     * @param roleId roleId
     * @return SeHomePageNotice
     */
    SeHomePageNotice selectHomePageNoticeByRole(int roleId);

    /**
     * selectRelationRoleList 通过首页通知id查询角色id列表
     *
     * @param hnId hnId
     * @return List<Integer>
     */
    List<Integer> selectRelationRoleList(int hnId);

    /**
     * selectHomePageNoticeList4Page 根据查询条件查询首页通知列表
     *
     * @param qb qb
     * @return List<SeHomePageNotice>
     */
    List<SeHomePageNotice> selectHomePageNoticeList4Page(QueryBvo<SeHomePageNotice> qb);

    /**
     * selectHomePageNoticeListCount 根据查询条件查询首页通知条数
     *
     * @param qb qb
     * @return int
     */
    int selectHomePageNoticeListCount(QueryBvo<SeHomePageNotice> qb);
}
