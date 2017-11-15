package com.elichn.pub.core.dao.security;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.pojo.security.SeLog;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: SeLogDao</p>
 * <p>Description: SeLogDao</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface SeLogDao {

    /**
     * selectLogList4Page 根据查询条件分页查询操作日志列表
     *
     * @param queryBvo queryBvo
     * @return List<SeLog>
     */
    List<SeLog> selectLogList4Page(QueryBvo<SeLog> queryBvo);

    /**
     * selectLogListCount 根据查询条件查询操作日志条数
     *
     * @param queryBvo queryBvo
     * @return int
     */
    int selectLogListCount(QueryBvo<SeLog> queryBvo);

    /**
     * selectLogsList 导出-根据查询条件查询操作日志列表
     *
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> selectLogsList();
}
