package com.elichn.pub.core.dao.security;

import com.elichn.pub.core.model.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.pojo.security.SeUser;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeUserDao</p>
 * <p>Description: SeUserDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeUserDao {

    /**
     * selectUserByUserName 根据用户名查询用户
     *
     * @param userName userName
     * @return SeUser
     */
    SeUser selectUserByUserName(String userName);

    /**
     * selectUsersList4Page 根据查询条件分页查询用户列表
     *
     * @param map map
     * @return List<SeUserRoleBvo>
     */
    List<SeUserRoleBvo> selectUsersList4Page(Map map);

    /**
     * selectUsersListCount 根据查询条件查询用户条数
     *
     * @param map map
     * @return Integer
     */
    Integer selectUsersListCount(Map map);

    /**
     * selectUserListByEmail 根据邮箱email判断该邮箱是否已经被使用
     *
     * @param email email
     * @return List<SeUser>
     */
    List<SeUser> selectUserListByEmail(String email);

    /**
     * selectUserListByRoleIds 根据角色id列表查询用户列表
     *
     * @param roleIds roleIds
     * @return List<SeUser>
     */
    List<SeUser> selectUserListByRoleIds(List<Integer> roleIds);

    /**
     * selectUserListByRoleId 根据角色id查询用户列表
     *
     * @param roleId roleId
     * @return List<SeUser>
     */
    List<SeUser> selectUserListByRoleId(Integer roleId);

    /**
     * selectUserListByCodes 根据角色code列表查询用户列表
     *
     * @param code code
     * @return List<SeUser>
     */
    List<SeUser> selectUserListByCodes(List<String> code);
}
