package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRescRoleKey;

import java.util.List;

/**
 * <p>Title: SeRoleRescService</p>
 * <p>Description: SeRoleRescService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeRoleRescService {

    /**
     * selectRescById 根据rescId查询一条资源信息
     *
     * @param rescId rescId
     * @return SeResc
     */
    SeResc selectRescById(Integer rescId);

    /**
     * selectAllRescsList 查询所有资源列表
     *
     * @return List<SeResc>
     */
    List<SeResc> selectAllRescsList();

    /**
     * selectRescRoleKeyByRoleId 根据roleId取得资源角色中间表
     *
     * @param roleId roleId
     * @return List<SeRescRoleKey>
     */
    List<SeRescRoleKey> selectRescRoleKeyByRoleId(Integer roleId);

    /**
     * insertResc 插入资源信息
     *
     * @param resc resc
     * @return SeResc
     */
    SeResc insertResc(SeResc resc);

    /**
     * deleteResc 递归删除资源
     *
     * @param rescId rescId
     */
    void deleteResc(Integer rescId);

    /**
     * rescId 根据资源id删除当前资源信息
     *
     * @param rescId rescId
     * @return int
     */
    int deleteRescById(Integer rescId);

    /**
     * deleteRescRoleByRescId 根据rescId删除角色资源中间表信息
     *
     * @param rescId rescId
     * @return int
     */
    int deleteRescRoleByRescId(Integer rescId);

    /**
     * deleteRescRoleByRoleId 根据id删除角色资源中间表信息
     *
     * @param roleId roleId
     * @return int
     */
    int deleteRescRoleByRoleId(Integer roleId);

    /**
     * updateResc 修改资源信息
     *
     * @param resc resc
     * @return int
     */
    int updateResc(SeResc resc);

    /**
     * insertRescRole 添加角色资源中间表信息
     *
     * @param rescRole rescRole
     * @return int
     */
    int insertRescRole(SeRescRoleKey rescRole);

    /**
     * selectRescByFatherId 根据资源id获取他下面的子孩子
     *
     * @param fatherId fatherId
     * @return List<SeResc>
     */
    List<SeResc> selectRescByFatherId(Integer fatherId);

    /**
     * selectRescListByRoleId  根据roleId查询资源列表(获取指定role的资源树)
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

    /**
     * deleteByPrimaryKey 根据主键删除角色资源中间表信息
     *
     * @param key key
     * @return int
     */
    int deleteByPrimaryKey(SeRescRoleKey key);

    /**
     * selectRescListByIds 根据资源id列表获取所有资源
     *
     * @param rescIds
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByIds(List<Integer> rescIds);

    /**
     * selectRoleIdByRescId 根据资源id查询出拥有该资源的角色id
     *
     * @param rescId rescId
     * @return List<SeRescRoleKey>
     */
    List<SeRescRoleKey> selectRoleIdByRescId(Integer rescId);

    /**
     * 根据userId查询资源列表(获取用户拥有的资源)
     *
     * @param userId userId
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByUserId(Integer userId);
}
