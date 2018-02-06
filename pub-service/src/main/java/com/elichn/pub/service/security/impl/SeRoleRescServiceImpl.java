package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.dao.security.SeRoleRescDao;
import com.elichn.pub.core.model.mapper.security.SeRescMapper;
import com.elichn.pub.core.model.mapper.security.SeRescRoleMapper;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRescExample;
import com.elichn.pub.core.model.pojo.security.SeRescRoleExample;
import com.elichn.pub.core.model.pojo.security.SeRescRoleKey;
import com.elichn.pub.service.security.SeRoleRescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SeRoleRescServiceImpl</p>
 * <p>Description: SeRoleRescServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeRoleRescServiceImpl implements SeRoleRescService {

    @Autowired
    private SeRoleRescDao seRoleRescDao;
    @Autowired
    private SeRescMapper seRescMapper;
    @Autowired
    private SeRescRoleMapper seRescRoleMapper;

    @Override
    public SeResc selectRescById(Integer rescId) {
        return seRescMapper.selectByPrimaryKey(rescId);
    }

    @Override
    public List<SeRescRoleKey> selectRescRoleKeyByRoleId(Integer roleId) {
        SeRescRoleExample rescRoleExample = new SeRescRoleExample();
        rescRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        return seRescRoleMapper.selectByExample(rescRoleExample);
    }

    @Override
    public int updateResc(SeResc resc) {
        return seRescMapper.updateByPrimaryKey(resc);
    }

    @Override
    public List<SeResc> selectAllRescsList() {
        return seRoleRescDao.selectAllRescsList();
    }

    @Override
    public SeResc insertResc(SeResc resc) {
        seRescMapper.insertSelective(resc);
        SeRescRoleKey rescRole = new SeRescRoleKey();
        rescRole.setRescId(resc.getId());
        rescRole.setRoleId(1);
        insertRescRole(rescRole);
        return resc;
    }

    @Override
    public void deleteResc(Integer rescId) {
        List<SeResc> tempRescList = this.selectRescByFatherId(rescId);
        for (SeResc resc : tempRescList) {
            this.deleteResc(resc.getId());
        }
        this.deleteRescRoleByRescId(rescId);
        this.deleteRescById(rescId);
    }

    @Override
    public int insertRescRole(SeRescRoleKey rescRole) {
        return seRescRoleMapper.insert(rescRole);
    }

    @Override
    public int deleteRescById(Integer rescId) {
        return seRescMapper.deleteByPrimaryKey(rescId);
    }

    @Override
    public int deleteRescRoleByRescId(Integer rescId) {
        SeRescRoleExample rescRoleExample = new SeRescRoleExample();
        rescRoleExample.createCriteria().andRescIdEqualTo(rescId);
        return seRescRoleMapper.deleteByExample(rescRoleExample);
    }

    @Override
    public int deleteRescRoleByRoleId(Integer roleId) {
        SeRescRoleExample rescRoleExample = new SeRescRoleExample();
        rescRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        return seRescRoleMapper.deleteByExample(rescRoleExample);
    }

    @Override
    public List<SeResc> selectRescByFatherId(Integer fatherId) {
        SeRescExample rescExample = new SeRescExample();
        rescExample.createCriteria().andFatherIdEqualTo(fatherId);
        return seRescMapper.selectByExample(rescExample);
    }

    @Override
    public List<SeResc> selectRescListByRoleId(Integer roleId) {
        // 通过角色获得资源
        return seRoleRescDao.selectRescListByRoleId(roleId);
    }

    @Override
    public List<SeResc> selectRescListByRoleIds(List<Integer> roleIds) {
        return seRoleRescDao.selectRescListByRoleIds(roleIds);
    }

    @Override
    public int deleteByPrimaryKey(SeRescRoleKey key) {
        return seRescRoleMapper.deleteByPrimaryKey(key);
    }

    @Override
    public List<SeResc> selectRescListByIds(List<Integer> rescIds) {
        if (rescIds != null && rescIds.size() > 0) {
            SeRescExample rescExample = new SeRescExample();
            SeRescExample.Criteria criteria = rescExample.createCriteria();
            criteria.andIdIn(rescIds);
            return seRescMapper.selectByExample(rescExample);
        } else {
            return new ArrayList<SeResc>();
        }
    }

    @Override
    public List<SeRescRoleKey> selectRoleIdByRescId(Integer rescId) {
        SeRescRoleExample example = new SeRescRoleExample();
        SeRescRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRescIdEqualTo(rescId);
        return seRescRoleMapper.selectByExample(example);
    }

    @Override
    public List<SeResc> selectRescListByUserId(Integer userId) {
        return seRoleRescDao.selectRescListByUserId(userId);
    }
}
