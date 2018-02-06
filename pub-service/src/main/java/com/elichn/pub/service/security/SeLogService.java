package com.elichn.pub.service.security;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.model.pojo.security.SeLog;

import java.util.List;
import java.util.Map;

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
     * insert 操作日志插入
     *
     * @param log log
     * @return int
     */
    int insert(SeLog log);

    /**
     * selectLogsList 根据查询条件分页查询操作日志列表
     *
     * @param queryBvo queryBvo
     * @return ResultBvo<SeLog>
     */
    ResultBvo<SeLog> selectLogList4Page(QueryBvo<SeLog> queryBvo);

    /**
     * insert 操作日志插入
     *
     * @param logType    日志类型
     * @param logContent 日志内容
     * @param ip         ip
     * @return int
     */
    int insert(String logType, String logContent, String ip);

    /**
     * insert 操作日志插入
     *
     * @param logType    日志类型
     * @param logContent 日志内容
     * @param ip         ip
     * @param userName   userName
     * @return int
     */
    int insert(String logType, String logContent, String ip, String userName);

    /**
     * selectLogsList 导出-根据查询条件查询操作日志列表
     *
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> selectLogsList();
}
