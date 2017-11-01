package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.model.pub.dao.security.SeRoleDao;
import com.elichn.pub.core.model.pub.mapper.security.SeRoleMapper;
import com.elichn.pub.core.model.pub.mapper.security.SeUserRoleMapper;
import com.elichn.pub.core.model.pub.pojo.security.SeRole;
import com.elichn.pub.core.model.pub.pojo.security.SeRoleExample;
import com.elichn.pub.core.model.pub.pojo.security.SeUserRoleKey;
import com.elichn.pub.service.security.SeRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SeRoleServiceImpl</p>
 * <p>Description: SeRoleServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeRoleServiceImpl implements SeRoleService {

    @Autowired
    private SeRoleDao seRoleDao;
    @Autowired
    private SeRoleMapper seRoleMapper;
    @Autowired
    private SeUserRoleMapper seUserRoleMapper;

    @Override
    public List<SeRole> selectRoleListByUser(String userId) {
        return seRoleDao.selectRoleListByUser(userId);
    }

    @Override
    public List<SeRole> selectRoles() {
        return seRoleMapper.selectByExample(null);
    }

    @Override
    public SeRole selectRoleById(Integer roleId) {
        return seRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int insertUserRoleKey(SeUserRoleKey userRoleKey) {
        return seUserRoleMapper.insert(userRoleKey);
    }

    @Override
    public int deleteByPrimaryKey(SeUserRoleKey userRoleKey) {
        return seUserRoleMapper.deleteByPrimaryKey(userRoleKey);
    }

    @Override
    public SeRole insertRole(SeRole role) {
        seRoleMapper.insert(role);
        return role;
    }

    @Override
    public Boolean isRole(String roleName, Integer id) {
        SeRoleExample roleExample = new SeRoleExample();
        SeRoleExample.Criteria criteria = roleExample.createCriteria();
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        criteria.andRoleNameEqualTo(roleName);
        List<SeRole> roleList = seRoleMapper.selectByExample(roleExample);
        return !roleList.isEmpty();
    }

    @Override
    public int deleteRoleById(Integer roleId) {
        return seRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int updateRole(SeRole role) {
        return seRoleMapper.updateByPrimaryKey(role);
    }

    @Override
    public List<SeRole> selectRolesByUser(Integer id) {
        List<SeRole> roles = seRoleDao.selectRolesByUser(id);

        List<SeRole> children = new ArrayList<SeRole>();
        for (SeRole role : roles) {
            children.addAll(this.getChildRoles(role.getId()));
        }
        roles.addAll(children);
        return roles;
    }

    @Override
    public List<SeRole> selectByPid(Integer pid) {
        SeRoleExample example = new SeRoleExample();
        SeRoleExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(pid);
        return seRoleMapper.selectByExample(example);
    }

    @Override
    public SeRole selectRoleByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            return seRoleDao.selectRoleByCode(code);
        } else {
            return null;
        }
    }

    /**
     * getChildRoles 获取指定的所有子孙节点
     *
     * @param id id
     * @return List<SeRole>
     */
    public List<SeRole> getChildRoles(Integer id) {
        SeRoleExample example = new SeRoleExample();
        SeRoleExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<SeRole> roles = seRoleMapper.selectByExample(example);
        List<SeRole> list = new ArrayList<SeRole>();
        if (roles != null && roles.size() > 0) {
            list.addAll(roles);
            for (SeRole role : roles) {
                list.addAll(this.getChildRoles(role.getId()));
            }
        }
        return list;
    }
}
