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
     * selectRescById 通过id取得一条资源信息
     *
     * @param rescId rescId
     * @return SeResc
     */
    SeResc selectRescById(Integer rescId);

    /**
     * getRescByUserId 获取用户拥有的资源
     *
     * @param id id
     * @return List<SeResc>
     */
    List<SeResc> getRescByUserId(int id);

    /**
     * selectRescs 获得所有资源信息集合
     *
     * @return List<SeResc>
     */
    List<SeResc> selectRescs();

    /**
     * selectRescRoleKeyByRoleId 通过角色id取得中间表
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
     * rescId 通过id删除当前资源信息
     *
     * @param rescId rescId
     * @return int
     */

    int deleteRescById(Integer rescId);

    /**
     * deleteRescRoleByRescId 通过rescId删除角色资源中间表信息
     *
     * @param rescId rescId
     * @return int
     */
    int deleteRescRoleByRescId(Integer rescId);

    /**
     * deleteRescRoleByRoleId 通过id删除角色资源中间表信息
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
     * getRescByFatherId根据资源id获取他下面的子孩子
     *
     * @param fatherId fatherId
     * @return List<SeResc>
     */
    List<SeResc> getRescByFatherId(Integer fatherId);

    /**
     * getRescByRole  获取指定role的资源树
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

    /**
     * deleteByPrimaryKey deleteByPrimaryKey
     *
     * @param key key
     * @return int
     */
    int deleteByPrimaryKey(SeRescRoleKey key);

    /**
     * getRescByIds 根据资源id获取所以资源
     *
     * @param rescIds
     * @return List<SeResc>
     */
    List<SeResc> getRescByIds(List<Integer> rescIds);

    /**
     * getRoleIdByRescId 根据资源id查询出拥有该资源的角色id
     *
     * @param rescId rescId
     * @return List<SeRescRoleKey>
     */
    List<SeRescRoleKey> getRoleIdByRescId(Integer rescId);
}
