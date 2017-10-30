package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pub.pojo.security.SeResc;

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
     * selectByPrimaryKey selectByPrimaryKey
     *
     * @param id id
     * @return SeResc
     */
    SeResc selectByPrimaryKey(Integer id);

    /**
     * getRescByName  getRescByName
     *
     * @param name name
     * @return List<SeResc>
     */
    List<SeResc> getRescByName(String name);
}
