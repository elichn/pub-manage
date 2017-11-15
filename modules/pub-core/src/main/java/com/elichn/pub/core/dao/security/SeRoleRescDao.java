package com.elichn.pub.core.dao.security;

import com.elichn.pub.core.model.pojo.security.SeResc;

import java.util.List;

/**
 * <p>Title: SeRoleRescDao</p>
 * <p>Description: SeRoleRescDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeRoleRescDao {

    /**
     * selectAllRescsList 查询所有资源列表
     *
     * @return List<SeResc>
     */
    List<SeResc> selectAllRescsList();

    /**
     * selectRescListByUserId 根据userId查询资源列表(获取用户拥有的资源)
     *
     * @param userId userId
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByUserId(int userId);

    /**
     * selectRescListByRoleId 根据roleId查询资源列表(获取角色拥有的资源)
     *
     * @param roleId roleId
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByRoleId(Integer roleId);

    /**
     * selectRescListByRoleIds 根据roleId列表查询资源列表
     *
     * @param roleIds roleIds
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByRoleIds(List<Integer> roleIds);
}
