package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.model.mapper.security.SeRescMapper;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRescExample;
import com.elichn.pub.service.security.SeRescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: SeRescServiceImpl</p>
 * <p>Description: SeRescServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeRescServiceImpl implements SeRescService {

    @Autowired
    private SeRescMapper seRescMapper;

    @Override
    public SeResc selectByPrimaryKey(Integer id) {
        return seRescMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SeResc> getRescByName(String name) {
        SeRescExample example = new SeRescExample();
        SeRescExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        return seRescMapper.selectByExample(example);
    }
}
