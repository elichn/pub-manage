package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.model.pub.bvo.QueryBvo;
import com.elichn.pub.core.model.pub.bvo.ResultBvo;
import com.elichn.pub.core.model.pub.dao.security.SeHomePageNoticeDao;
import com.elichn.pub.core.model.pub.dao.security.SeRoleDao;
import com.elichn.pub.core.model.pub.mapper.security.SeHomePageNoticeMapper;
import com.elichn.pub.core.model.pub.mapper.security.SeRoleNoticeMapper;
import com.elichn.pub.core.model.pub.pojo.security.SeHomePageNotice;
import com.elichn.pub.core.model.pub.pojo.security.SeRole;
import com.elichn.pub.core.model.pub.pojo.security.SeRoleNotice;
import com.elichn.pub.core.model.pub.pojo.security.SeRoleNoticeExample;
import com.elichn.pub.service.security.SeHomePageNoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void insert(SeHomePageNotice record, int[] roleIds) {
        record.setCreateTime(DateTime.now());
        seHomePageNoticeMapper.insert(record);
        if (roleIds != null) {
            for (int roleId : roleIds) {
                SeRoleNotice rn = new SeRoleNotice();
                rn.setNoticeId(record.getId());
                rn.setRoleId(roleId);
                seRoleNoticeMapper.insert(rn);
            }
        }
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(SeHomePageNotice record) {
        SeHomePageNotice h = seHomePageNoticeMapper.selectByPrimaryKey(record.getId());
        if (h != null) {
            if (h.getType() == 1) {
                h.setUrl(record.getUrl());
            } else {
                h.setContent(record.getContent());
            }
            h.setUpdateTime(DateTime.now());
            return seHomePageNoticeMapper.updateByPrimaryKeyWithBLOBs(h);
        }
        return 0;
    }

    @Transactional
    @Override
    public void updateRoleNotice(int hnId, List<Integer> roleIds) {
        List<Integer> old = seHomePageNoticeDao.getRelationRole(hnId);
        // 取得交集
        List<Integer> intersection = (List<Integer>) CollectionUtils
                .intersection(roleIds, old);
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
    public int updateStatus(int hnId, int status) {
        SeHomePageNotice h = seHomePageNoticeMapper.selectByPrimaryKey(hnId);
        if (h != null) {
            status = status != 0 ? 1 : 0;
            h.setStatus(status);
            h.setUpdateTime(DateTime.now());
            seHomePageNoticeMapper.updateByPrimaryKey(h);
            return 1;
        }
        return 0;
    }

    @Override
    public SeHomePageNotice getHomePageNoticeByUser(int userId) {
        List<SeRole> roles = seRoleDao.selectRolesByUser(userId);
        SeHomePageNotice pn = null;
        for (SeRole r : roles) {
            SeHomePageNotice hn = seHomePageNoticeDao.getHomePageNoticeByRole(r.getId());

            if (hn != null && (pn == null || pn.getUpdateTime().isBefore(hn.getUpdateTime()))) {
                pn = hn;
            }
        }
        return pn;
    }

    @Override
    public ResultBvo<SeHomePageNotice> getHomePageNoticeList(QueryBvo<SeHomePageNotice> qb) {
        ResultBvo<SeHomePageNotice> resultBvo = new ResultBvo<SeHomePageNotice>();
        resultBvo.setList(seHomePageNoticeDao.getHomePageNoticeList(qb));
        resultBvo.setTotal(seHomePageNoticeDao.getHomePageNoticeListCount(qb));
        return resultBvo;
    }

    @Override
    public List<Integer> getRelationRole(int hnId) {
        return seHomePageNoticeDao.getRelationRole(hnId);
    }

    @Override
    public int updateAsNew(int id) {
        SeHomePageNotice h = seHomePageNoticeMapper.selectByPrimaryKey(id);
        if (h != null) {
            h.setUpdateTime(DateTime.now());
            seHomePageNoticeMapper.updateByPrimaryKey(h);
            return 1;
        }
        return 0;
    }
}
