package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pojo.security.SePublicPermission;

import java.util.List;

/**
 * <p>Title: SePublicPermissionService</p>
 * <p>Description: SePublicPermissionService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SePublicPermissionService {

    /**
     * selectBySystem 根据system查询PublicPermission
     *
     * @param system system
     * @return List<SePublicPermission>
     */
    List<SePublicPermission> selectBySystem(String system);
}
