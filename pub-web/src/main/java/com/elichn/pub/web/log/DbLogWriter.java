package com.elichn.pub.web.log;

import com.elichn.pub.service.security.SeLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Title: DbLogWriter</p>
 * <p>Description: DbLogWriter</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class DbLogWriter implements LogWriter {

    @Autowired
    private SeLogService seLogService;

    @Override
    public void write(String logType, String logDetail, String ip, String userName) {
        seLogService.insert(logType, logDetail, ip, userName);
    }
}
