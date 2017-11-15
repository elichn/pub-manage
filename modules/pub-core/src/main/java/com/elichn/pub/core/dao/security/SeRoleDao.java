package com.elichn.pub.core.dao.security;


import com.elichn.pub.core.model.pojo.security.SeRole;

import java.util.List;

/**
 * <p>Title: SeRoleDao</p>
 * <p>Description: SeRoleDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeRoleDao {

    /**
     * selectRoleListByUserId 根据userId查询角色列表
     *
     * @param userId userId
     * @return List<SeRole>
     */
    List<SeRole> selectRoleListByUserId(Integer userId);

    /**
     * selectRoleByCode 根据角色code查询角色
     *
     * @param code code
     * @return SeRole
     */
    SeRole selectRoleByCode(String code);
}
