package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.dao.security.SePublicPermissionDao;
import com.elichn.pub.core.model.pojo.security.SePublicPermission;
import com.elichn.pub.service.security.SePublicPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: SePublicPermissionServiceImpl</p>
 * <p>Description: SePublicPermissionServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SePublicPermissionServiceImpl implements SePublicPermissionService {

    @Autowired
    private SePublicPermissionDao sePublicPermissionDao;

    @Override
    public List<SePublicPermission> selectBySystem(String system) {
        return sePublicPermissionDao.selectBySystem(system);
    }
}
