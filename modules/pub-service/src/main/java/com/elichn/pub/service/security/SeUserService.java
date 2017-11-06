package com.elichn.pub.service.security;

import com.elichn.pub.core.model.bvo.SeUserBvo;
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
     * selectUsersByPage 分页查询用户
     *
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @param map      map
     * @return List<SeUserRoleBvo>
     */
    List<SeUserRoleBvo> selectUsersByPage(int pageNo, int pageSize, Map map);

    /**
     * getRowsByName 根据名字查找用户的总记录数
     *
     * @param map map
     * @return Integer
     */
    Integer getRowsByName(Map map);

    /**
     * verifyEmail 验证邮箱似否已被使用
     *
     * @param email email
     * @return boolean
     */
    boolean verifyEmail(String email);

    /**
     * updateByPrimaryKey updateByPrimaryKey
     *
     * @param record record
     * @return int
     */
    int updateByPrimaryKey(SeUser record);

    /**
     * getUserByRole  getUserByRole
     *
     * @param ids ids
     * @return List<SeUser>
     */
    List<SeUser> getUserByRole(List<Integer> ids);

    /**
     * getUserByRoleId
     *
     * @param roleId roleId
     * @return List<SeUser>
     */
    List<SeUser> getUserByRoleId(Integer roleId);

    /**
     * getUsersByCode 根据code查询用户
     *
     * @param code code
     * @return List<SeUser>
     */
    List<SeUser> getUsersByCode(List<String> code);

    /**
     * getUserInfo  getUserInfo
     *
     * @param userName
     * @return SeUserBvo
     */
    SeUserBvo getUserInfo(String userName);

    /**
     * getUserInfoById getUserInfoById
     *
     * @param userId userId
     * @return SeUserBvo
     */
    SeUserBvo getUserInfoById(Integer userId);
}
