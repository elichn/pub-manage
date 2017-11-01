package com.elichn.pub.service.security.impl;


import com.elichn.pub.core.model.pub.bvo.SeUserBvo;
import com.elichn.pub.core.model.pub.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.pub.dao.security.SeUserDao;
import com.elichn.pub.core.model.pub.mapper.security.SeUserMapper;
import com.elichn.pub.core.model.pub.mapper.security.SeUserRoleMapper;
import com.elichn.pub.core.model.pub.pojo.security.SeUser;
import com.elichn.pub.core.model.pub.pojo.security.SeUserRoleExample;
import com.elichn.pub.service.security.SeUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeUserServiceImpl</p>
 * <p>Description: SeUserServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeUserServiceImpl implements SeUserService {

    @Autowired
    private SeUserDao seUserDao;
    @Autowired
    private SeUserMapper seUserMapper;
    @Autowired
    private SeUserRoleMapper seUserRoleMapper;

    @Override
    public SeUser selectByName(String username) {
        return seUserDao.selectUserByUserName(username);
    }

    @Override
    public SeUser selectUserById(Integer userid) {
        return seUserMapper.selectByPrimaryKey(userid);
    }

    @Override
    public int insertUser(SeUser user) {
        user.setCreateTime(DateTime.now());
        return seUserMapper.insert(user);
    }

    @Override
    public int updateUser(SeUser user) {
        return seUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<SeUserRoleBvo> selectUsersByPage(int pageNo, int pageSize, Map map) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 20;
        }
        int startCount = (pageNo - 1) * pageSize;
        map.put("startCount", startCount);
        map.put("pageSize", pageSize);
        return seUserDao.selectUsersByPage(map);
    }

    @Override
    public Integer getRowsByName(Map map) {
        return seUserDao.selectUsersByNameRows(map);
    }

    @Override
    public int deleteUserRoleByRoleId(Integer roleId) {
        SeUserRoleExample userRoleExample = new SeUserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        return seUserRoleMapper.deleteByExample(userRoleExample);
    }

    @Override
    public boolean verifyEmail(String email) {
        boolean flag = false;
        List<SeUser> userList = seUserDao.selectUserByEmail(email);
        if (userList != null && userList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public int updateByPrimaryKey(SeUser record) {
        return seUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SeUser> getUserByRole(List<Integer> ids) {
        return seUserDao.getUserByRole(ids);
    }

    @Override
    public List<SeUser> getUserByRoleId(Integer roleId) {
        return seUserDao.getUserByRoleId(roleId);
    }

    @Override
    public List<SeUser> getUsersByCode(List<String> code) {
        return seUserDao.getUsersByCode(code);
    }

    @Override
    public SeUserBvo getUserInfo(String userName) {
        return seUserDao.getUserInfo(userName);
    }

    @Override
    public SeUserBvo getUserInfoById(Integer userId) {
        return seUserDao.getUserInfoById(userId);
    }
}
