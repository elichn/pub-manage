package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.dao.security.SeHomePageNoticeDao;
import com.elichn.pub.core.dao.security.SeRoleDao;
import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.model.mapper.security.SeHomePageNoticeMapper;
import com.elichn.pub.core.model.mapper.security.SeRoleNoticeMapper;
import com.elichn.pub.core.model.pojo.security.SeHomePageNotice;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeRoleNotice;
import com.elichn.pub.core.model.pojo.security.SeRoleNoticeExample;
import com.elichn.pub.service.security.SeHomePageNoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: SeHomePageNoticeServiceImpl</p>
 * <p>Description: SeHomePageNoticeServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeHomePageNoticeServiceImpl implements SeHomePageNoticeService {

    @Autowired
    private SeHomePageNoticeMapper seHomePageNoticeMapper;
    @Autowired
    private SeHomePageNoticeDao seHomePageNoticeDao;
    @Autowired
    private SeRoleNoticeMapper seRoleNoticeMapper;
    @Autowired
    private SeRoleDao seRoleDao;

    @Override
    public void insert(SeHomePageNotice record, int[] roleIds) {
        record.setCreateTime(DateTime.now());
        seHomePageNoticeMapper.insert(record);
        if (roleIds == null) {
            return;
        }
        for (int roleId : roleIds) {
            SeRoleNotice rn = new SeRoleNotice();
            rn.setNoticeId(record.getId());
            rn.setRoleId(roleId);
            seRoleNoticeMapper.insert(rn);
        }
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(SeHomePageNotice record) {
        SeHomePageNotice hn = seHomePageNoticeMapper.selectByPrimaryKey(record.getId());
        if (hn == null) {
            return 0;
        }
        if (hn.getType() == 1) {
            hn.setUrl(record.getUrl());
        } else {
            hn.setContent(record.getContent());
        }
        hn.setUpdateTime(DateTime.now());
        return seHomePageNoticeMapper.updateByPrimaryKeyWithBLOBs(hn);
    }

    @Override
    public void updateRoleNotice(Integer hnId, List<Integer> roleIds) {
        List<Integer> old = seHomePageNoticeDao.selectRelationRoleList(hnId);
        // 取得交集
        List<Integer> intersection = (List<Integer>) CollectionUtils.intersection(roleIds, old);
        // 获取需要删除的
        List<Integer> delRel = (List<Integer>) CollectionUtils.disjunction(old, intersection);
        // 获取第二个文件中与交集的差集
        List<Integer> addRel = (List<Integer>) CollectionUtils.disjunction(roleIds, intersection);
        for (int r : delRel) {
            SeRoleNoticeExample e = new SeRoleNoticeExample();
            SeRoleNoticeExample.Criteria criteria = e.createCriteria();
            criteria.andRoleIdEqualTo(r);
            criteria.andNoticeIdEqualTo(hnId);
            seRoleNoticeMapper.deleteByExample(e);
        }
        for (int r : addRel) {
            SeRoleNotice rn = new SeRoleNotice();
            rn.setNoticeId(hnId);
            rn.setRoleId(r);
            seRoleNoticeMapper.insert(rn);
        }
    }

    @Override
    public int updateStatus(Integer hnId, Integer status) {
        SeHomePageNotice hn = seHomePageNoticeMapper.selectByPrimaryKey(hnId);
        if (hn == null) {
            return 0;
        }
        status = status != 0 ? 1 : 0;
        hn.setStatus(status);
        hn.setUpdateTime(DateTime.now());
        seHomePageNoticeMapper.updateByPrimaryKey(hn);
        return 1;
    }

    @Override
    public SeHomePageNotice selectHomePageNoticeByUserId(Integer userId) {
        List<SeRole> roles = seRoleDao.selectRoleListByUserId(userId);
        SeHomePageNotice pn = null;
        for (SeRole r : roles) {
            SeHomePageNotice hn = seHomePageNoticeDao.selectHomePageNoticeByRole(r.getId());
            boolean isHn = (pn == null || pn.getUpdateTime().isBefore(hn.getUpdateTime()));
            if (hn != null && isHn) {
                pn = hn;
            }
        }
        return pn;
    }

    @Override
    public ResultBvo<SeHomePageNotice> selectHomePageNoticeList4Page(QueryBvo<SeHomePageNotice> qb) {
        ResultBvo<SeHomePageNotice> resultBvo = new ResultBvo<SeHomePageNotice>();
        resultBvo.setList(seHomePageNoticeDao.selectHomePageNoticeList4Page(qb));
        resultBvo.setTotal(seHomePageNoticeDao.selectHomePageNoticeListCount(qb));
        return resultBvo;
    }

    @Override
    public List<Integer> selectRelationRoleList(Integer hnId) {
        return seHomePageNoticeDao.selectRelationRoleList(hnId);
    }

    @Override
    public int updateAsNew(Integer id) {
        SeHomePageNotice hn = seHomePageNoticeMapper.selectByPrimaryKey(id);
        if (hn == null) {
            return 0;
        }
        hn.setUpdateTime(DateTime.now());
        seHomePageNoticeMapper.updateByPrimaryKey(hn);
        return 1;
    }
}
