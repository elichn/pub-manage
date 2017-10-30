package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pub.pojo.security.SeRole;
import com.elichn.pub.core.model.pub.pojo.security.SeUserRoleKey;

import java.util.List;

/**
 * <p>Title: SeRoleService</p>
 * <p>Description: SeRoleService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeRoleService {

    /**
     * selectRoleListByUser selectRoleListByUser
     *
     * @param userId userId
     * @return List<SeRole> 用户具有的role集合
     */
    List<SeRole> selectRoleListByUser(String userId);

    /**
     * selectRoles 获得角色列表
     *
     * @return List<SeRole>
     */

    List<SeRole> selectRoles();

    /**
     * insertUserRoleKey 保存用户角色中间表
     *
     * @param userRoleKey userRoleKey
     * @return int
     */
    int insertUserRoleKey(SeUserRoleKey userRoleKey);

    /**
     * deleteByPrimaryKey deleteByPrimaryKey
     *
     * @param userRoleKey userRoleKey
     * @return int
     */
    int deleteByPrimaryKey(SeUserRoleKey userRoleKey);

    /**
     * insertRole 保存角色信息
     *
     * @param role role
     * @return SeRole
     */
    SeRole insertRole(SeRole role);

    /**
     * deleteRoleById 通过角色id删除角色信息
     *
     * @param roleId roleId
     * @return int
     */
    int deleteRoleById(Integer roleId);

    /**
     * selectRoleById 通过角色id查询角色信息
     *
     * @param roleId roleId
     * @return SeRole
     */
    SeRole selectRoleById(Integer roleId);

    /**
     * updateRole 修改角色信息
     *
     * @param role role
     * @return int
     */
    int updateRole(SeRole role);

    /**
     * isRole isRole
     *
     * @param descn descn
     * @param id    id
     * @return Boolean
     */
    Boolean isRole(String descn, Integer id);

    /**
     * selectRolesByUser 获取指定user拥有的role列表
     *
     * @param id id
     * @return List<SeRole>
     */
    List<SeRole> selectRolesByUser(Integer id);

    /**
     * selectByPid selectByPid
     *
     * @param pid pid
     * @return List<SeRole>
     */
    List<SeRole> selectByPid(Integer pid);

    /**
     * selectRoleByCode 根据code获得role
     *
     * @param code code
     * @return SeRole
     */
    SeRole selectRoleByCode(String code);
}
