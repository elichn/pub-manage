package com.elichn.pub.core.model.pub.dao.security;

import com.elichn.pub.core.model.pub.pojo.security.SePublicPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Title: SePublicPermissionDao</p>
 * <p>Description: SePublicPermissionDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SePublicPermissionDao {

    /**
     * selectBySystem selectBySystem
     *
     * @param system system
     * @return List<SePublicPermission>
     */
    List<SePublicPermission> selectBySystem(@Param("system") String system);
}
