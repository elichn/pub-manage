package com.elichn.pub.web.log;

/**
 * <p>Title: LogWriter</p>
 * <p>Description: LogWriter</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface LogWriter {

    /**
     * write write
     *
     * @param logType   logType
     * @param logDetail logDetail
     * @param ip        ip
     * @param userName  userName
     */
    void write(String logType, String logDetail, String ip, String userName);
}
