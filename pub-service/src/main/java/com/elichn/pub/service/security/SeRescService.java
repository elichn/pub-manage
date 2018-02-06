package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pojo.security.SeResc;

import java.util.List;

/**
 * <p>Title: SeRescService</p>
 * <p>Description: SeRescService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeRescService {

    /**
     * selectByPrimaryKey 根据主键查询资源
     *
     * @param id id
     * @return SeResc
     */
    SeResc selectByPrimaryKey(Integer id);

    /**
     * selectRescListByName  根据资源名称查询资源列表
     *
     * @param name name
     * @return List<SeResc>
     */
    List<SeResc> selectRescListByName(String name);
}
