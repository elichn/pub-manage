package com.elichn.pub.core.model.pub.dao.security;

import com.elichn.pub.core.model.pub.pojo.security.SeResc;

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
     * selectRescs selectRescs
     *
     * @return List<SeResc>
     */
    List<SeResc> selectRescs();

    /**
     * getRescByUserId getRescByUserId
     *
     * @param id id
     * @return List<SeResc>
     */
    List<SeResc> getRescByUserId(int id);

    /**
     * getRescByRole 获取角色拥有的资源
     *
     * @param id id
     * @return List<SeResc>
     */
    List<SeResc> getRescByRole(Integer id);

    /**
     * getRescList getRescList
     *
     * @param roleIds roleIds
     * @return List<SeResc>
     */
    List<SeResc> getRescList(List<Integer> roleIds);
}
