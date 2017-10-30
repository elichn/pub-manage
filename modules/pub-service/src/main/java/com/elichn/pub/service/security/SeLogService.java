package com.elichn.pub.service.security;

import com.elichn.pub.core.model.pub.bvo.QueryBvo;
import com.elichn.pub.core.model.pub.bvo.ResultBvo;
import com.elichn.pub.core.model.pub.pojo.security.SeLog;

/**
 * <p>Title: SeLogService</p>
 * <p>Description: SeLogService</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeLogService {

    /**
     * insert 记录操作日志
     *
     * @param log log
     * @return int
     */
    int insert(SeLog log);

    /**
     * getLogsList getLogsList
     *
     * @param queryBvo queryBvo
     * @return ResultBvo<SeLog>
     */
    ResultBvo<SeLog> getLogsList(QueryBvo<SeLog> queryBvo);

    /**
     * insert 插入日志
     *
     * @param logType    日志类型
     * @param logContent 日志内容
     * @param ip         ip
     * @return int
     */
    int insert(String logType, String logContent, String ip);

    /**
     * insert 插入日志
     *
     * @param logType    日志类型
     * @param logContent 日志内容
     * @param ip         ip
     * @param userName   userName
     * @return int
     */
    int insert(String logType, String logContent, String ip, String userName);
}
