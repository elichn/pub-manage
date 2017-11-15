package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUserRoleKey;

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
     * selectRoleListByUserId 根据userId查询角色列表
     *
     * @param userId userId
     * @return 用户具有的role列表
     */
    List<SeRole> selectRoleListByUserId(Integer userId);

    /**
     * selectAllRolesList 获得角色列表
     *
     * @return 所有角色列表
     */

    List<SeRole> selectAllRolesList();

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
     * selectRoleById 根据角色id查询角色信息
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
     * selectRolesListByUserId 根据userId查询拥有的角色列表
     *
     * @param userId userId
     * @return List<SeRole>
     */
    List<SeRole> selectRolesListByUserId(Integer userId);

    /**
     * selectRoleListByPid 根据pid查询角色列表
     *
     * @param pid pid
     * @return List<SeRole>
     */
    List<SeRole> selectRoleListByPid(Integer pid);

    /**
     * selectRoleByCode 根据code查询role
     *
     * @param code code
     * @return SeRole
     */
    SeRole selectRoleByCode(String code);
}
