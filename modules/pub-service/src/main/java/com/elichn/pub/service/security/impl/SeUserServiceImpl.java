package com.elichn.pub.service.security.impl;


import com.elichn.pub.core.dao.security.SeUserDao;
import com.elichn.pub.core.model.bvo.SeUserRoleBvo;
import com.elichn.pub.core.model.mapper.security.SeUserMapper;
import com.elichn.pub.core.model.mapper.security.SeUserRoleMapper;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.core.model.pojo.security.SeUserRoleExample;
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
    public SeUser selectByName(String userName) {
        return seUserDao.selectUserByUserName(userName);
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
    public List<SeUserRoleBvo> selectUsersList4Page(int pageNo, int pageSize, Map map) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        int start = (pageNo - 1) * pageSize;
        map.put("start", start);
        map.put("pageSize", pageSize);
        return seUserDao.selectUsersList4Page(map);
    }

    @Override
    public Integer selectUsersListCount(Map map) {
        return seUserDao.selectUsersListCount(map);
    }

    @Override
    public int deleteUserRoleByRoleId(Integer roleId) {
        SeUserRoleExample userRoleExample = new SeUserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(roleId);
        return seUserRoleMapper.deleteByExample(userRoleExample);
    }

    @Override
    public int updateByPrimaryKey(SeUser record) {
        return seUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean verifyEmail(String email) {
        boolean flag = false;
        List<SeUser> userList = seUserDao.selectUserListByEmail(email);
        if (userList != null && userList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<SeUser> selectUserListByRoleIds(List<Integer> roleIds) {
        return seUserDao.selectUserListByRoleIds(roleIds);
    }

    @Override
    public List<SeUser> selectUserListByRoleId(Integer roleId) {
        return seUserDao.selectUserListByRoleId(roleId);
    }

    @Override
    public List<SeUser> selectUserListByCodes(List<String> code) {
        return seUserDao.selectUserListByCodes(code);
    }
}
