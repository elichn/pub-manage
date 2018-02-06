package com.elichn.pub.service.security;

import com.elichn.pub.core.model.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.pojo.security.SeUser;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeUserService</p>
 * <p>Description: SeUserService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeUserService {

    /**
     * selectByName 通过用户名查询用户信息
     *
     * @param userName userName
     * @return SeUser
     */
    SeUser selectByName(String userName);

    /**
     * selectUserById 通过用户id查询用户信息
     *
     * @param userid userid
     * @return SeUser
     */
    SeUser selectUserById(Integer userid);

    /**
     * insertUser 保存用户信息
     *
     * @param user user
     * @return int
     */
    int insertUser(SeUser user);

    /**
     * deleteUserRoleByRoleId 通过角色id删除用户角色中间表
     *
     * @param roleId roleId
     * @return int
     */
    int deleteUserRoleByRoleId(Integer roleId);

    /**
     * updateUser 修改用户信息
     *
     * @param user user
     * @return int
     */
    int updateUser(SeUser user);

    /**
     * selectUsersList4Page 根据查询条件分页查询用户列表
     *
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @param map      map
     * @return List<SeUserRoleBvo>
     */
    List<SeUserRoleBvo> selectUsersList4Page(int pageNo, int pageSize, Map map);

    /**
     * selectUsersListCount 根据查询条件查询用户条数
     *
     * @param map map
     * @return Integer
     */
    Integer selectUsersListCount(Map map);

    /**
     * updateByPrimaryKey updateByPrimaryKey
     *
     * @param record record
     * @return int
     */
    int updateByPrimaryKey(SeUser record);

    /**
     * verifyEmail 验证邮箱似否已被使用
     *
     * @param email email
     * @return boolean
     */
    boolean verifyEmail(String email);

    /**
     * selectUserListByRoleIds  根据角色id列表查询用户列表
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
