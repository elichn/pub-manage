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
     * selectRoleListByUser selectRoleListByUser
     *
     * @param userId userId
     * @return List<SeRole>
     */
    List<SeRole> selectRoleListByUser(String userId);

    /**
     * selectRolesByUser selectRolesByUser
     *
     * @param id selectRoleListByUser
     * @return List<SeRole>
     */
    List<SeRole> selectRolesByUser(Integer id);

    /**
     * selectRoleByCode selectRoleByCode
     *
     * @param code code
     * @return SeRole
     */
    SeRole selectRoleByCode(String code);
}
