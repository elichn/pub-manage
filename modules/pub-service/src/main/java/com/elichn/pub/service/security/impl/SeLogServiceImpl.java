package com.elichn.pub.service.security.impl;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.dao.security.SeLogDao;
import com.elichn.pub.core.model.mapper.security.SeLogMapper;
import com.elichn.pub.core.model.pojo.security.SeLog;
import com.elichn.pub.service.security.SeLogService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeLogServiceImpl</p>
 * <p>Description: SeLogServiceImpl</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Service
public class SeLogServiceImpl implements SeLogService {

    private final static Logger LOG = LoggerFactory.getLogger(SeLogServiceImpl.class);

    @Autowired
    private SeLogMapper seLogMapper;
    @Autowired
    private SeLogDao seLogDao;

    @Override
    public int insert(SeLog log) {
        log.setLogTime(DateTime.now());
        return seLogMapper.insert(log);
    }

    @Override
    public int insert(String logType, String logContent, String ip) {
        if (StringUtils.isNotBlank(logType)) {
            SeLog log = new SeLog();
            log.setLogType(logType);
            log.setIp(ip);
            log.setLogContent(logContent);
            return this.insert(log);
        } else {
            LOG.info("未定义类型的操作");
            return -1;
        }
    }

    @Override
    public int insert(String logType, String logContent, String ip, String userName) {
        if (StringUtils.isNotBlank(logType)) {
            SeLog log = new SeLog();
            log.setLogType(logType);
            log.setIp(ip);
            log.setLogContent(logContent);
            log.setUserName(userName);
            return this.insert(log);
        } else {
            LOG.info("未定义类型的操作");
            return -1;
        }
    }

    @Override
    public ResultBvo<SeLog> selectLogList4Page(QueryBvo<SeLog> queryBvo) {
        ResultBvo<SeLog> resultBvo = new ResultBvo<SeLog>();
        resultBvo.setList(seLogDao.selectLogList4Page(queryBvo));
        resultBvo.setTotal(seLogDao.selectLogListCount(queryBvo));
        return resultBvo;
    }

    @Override
    public List<Map<String, Object>> selectLogsList() {
        return seLogDao.selectLogsList();
    }

    /**
     * getWheresString getWheresString
     *
     * @param userName userName
     * @param datetime datetime
     * @param logtype  logtype
     * @return String
     */
    private String getWheresString(String userName, String datetime, String logtype) {
        String where = "";
        if (StringUtils.isNotBlank(userName)) {
            where += " and user_name like '%" + userName + "%'";
        }
        if (datetime != null && StringUtils.isNotBlank(datetime)) {
            where += " and date(log_time)='" + datetime + "'";
        }
        if (StringUtils.isNotBlank(logtype)) {
            where += " and log_type='" + logtype + "'";
        }

        if (StringUtils.isNotBlank(where)) {
            where = " where" + where;
        }
        where = where.replace("where and", "where");
        return where;
    }
}
