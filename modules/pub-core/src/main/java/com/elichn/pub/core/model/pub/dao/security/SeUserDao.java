package com.elichn.pub.core.model.pub.dao.security;

import com.elichn.pub.core.model.pub.bvo.SeUserBvo;
import com.elichn.pub.core.model.pub.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.pub.pojo.security.SeUser;
import org.apache.ibatis.annotations.Param;

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
     * selectUserByUserName selectUserByUserName
     *
     * @param username username
     * @return SeUser
     */
    SeUser selectUserByUserName(String username);

    /**
     * selectUsersByPage selectUsersByPage
     *
     * @param map map
     * @return List<SeUserRoleBvo>
     */
    List<SeUserRoleBvo> selectUsersByPage(Map map);

    /**
     * selectUsersByNameRows selectUsersByNameRows
     *
     * @param map map
     * @return Integer
     */
    Integer selectUsersByNameRows(Map map);

    /**
     * selectUserByEmail 根据邮箱在user表中判断该邮箱是否已经被使用
     *
     * @param email email
     * @return List<SeUser>
     */
    List<SeUser> selectUserByEmail(String email);

    /**
     * getUserByRole getUserByRole
     *
     * @param ids ids
     * @return List<SeUser>
     */
    List<SeUser> getUserByRole(List<Integer> ids);

    /**
     * getUserByRoleId getUserByRoleId
     *
     * @param roleId roleId
     * @return List<SeUser>
     */
    List<SeUser> getUserByRoleId(Integer roleId);

    /**
     * getUsersByCode getUsersByCode
     *
     * @param code code
     * @return List<SeUser>
     */
    List<SeUser> getUsersByCode(List<String> code);

    /**
     * getUserInfo getUserInfo
     *
     * @param userName userName
     * @return SeUserBvo
     */
    SeUserBvo getUserInfo(@Param("userName") String userName);

    /**
     * getUserInfoById getUserInfoById
     *
     * @param userId userId
     * @return SeUserBvo
     */
    SeUserBvo getUserInfoById(@Param("userId") Integer userId);
}
