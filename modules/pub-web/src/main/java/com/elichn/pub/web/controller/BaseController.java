package com.elichn.pub.web.controller;

import com.elichn.pub.core.constant.CommonConstats;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.service.security.SeLogService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.aop.LogAspect;
import com.elichn.pub.web.util.DateTimeEditor;
import com.elichn.pub.web.util.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: BaseController</p>
 * <p>Description: BaseController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class BaseController<T> {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 成功
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * 失败
     */
    public static final String FAIL = "FAIL";
    /**
     * 参数错误
     */
    public static final String INVALID_PARAM = "INVALID_PARAM";
    /**
     * 重复
     */
    public static final String DUPLICATE = "DUPLICATE";
    /**
     * 超时
     */
    public static final String SESSION_TIME_OUT = "SESSION_TIME_OUT";
    /**
     * 没有权限
     */
    public static final String ACCESS_DENY = "ACCESS_DENY";
    /**
     * 用户不存在
     */
    public static final String USER_NOT_EXIST = "USER_NOT_EXIST";
    /**
     * 记录不存在
     */
    public static final String RECORD_NOT_EXIST = "RECORD_NOT_EXIST";
    /**
     * 已锁定
     */
    public static final String LOCKED = "LOCKED";
    /**
     * CHECK_TIMES
     */
    public static final String CHECK_TIMES = "CHECK_TIMES";
    /**
     * 错误
     */
    public static final String ERROR_KEY = "errorKey";
    /**
     * MSG
     */
    public static final String MSG = "msg";
    /**
     * DATAS
     */
    public static final String DATAS = "datas";


    @Autowired
    private SeLogService seLogService;
    @Autowired
    private SeUserService seUserService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor(true));
    }

    /**
     * getRequestContext getRequestContext
     *
     * @param request request
     * @return
     */
    protected RequestContext getRequestContext(HttpServletRequest request) {
        return new RequestContext(request);
    }

    /**
     * getUserName 获得当前登录的用户名
     *
     * @return String
     */
    public String getUserName() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            return (String) currentUser.getPrincipal();
        } catch (Exception e) {
            LOG.warn("获得当前登录的用户名异常,", e);
            return null;
        }
    }

    /**
     * getCurrentUser 获得当前登录的用户\
     *
     * @return SeUser
     */
    public SeUser getCurrentUser() {
        String userName = getUserName();
        if (StringUtils.isNotBlank(userName)) {
            return seUserService.selectByName(userName);
        } else {
            LOG.warn("获得当前登录的用户为空,userName={}", userName);
            return null;
        }
    }

    /**
     * isAdmin 是否是超级管理员
     *
     * @return boolean
     */
    public boolean isAdmin() {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(String.valueOf(CommonConstats.SUPER_ADMIN));
    }

    /**
     * getRemoteAddr 获取客户端的ip地址 如果通过了代理，则获取Header中的X-Forwarded-For值
     * 当X-Forwarded-For值为 remoteAddr,proxy1,proxy2形式时，获取第一个逗号前面的即是客户端ip
     *
     * @param request request
     * @return String
     */
    protected String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        } else {
            String comma = ",";
            if (remoteAddr.contains(comma)) {
                remoteAddr = remoteAddr.substring(0, remoteAddr.indexOf(comma));
            }
        }
        return remoteAddr;
    }

    /**
     * writeLog writeLog
     *
     * @param request    request
     * @param logType    logType
     * @param logContent logContent
     */
    protected void writeLog(HttpServletRequest request, String logType, String logContent) {
        seLogService.insert(logType, logContent, getRemoteAddr(request), getUserName());
    }

    /**
     * writeLog writeLog
     *
     * @param request    request
     * @param logType    logType
     * @param logContent logContent
     * @param userName   userName
     */
    protected void writeLog(HttpServletRequest request, String logType, String logContent, String userName) {
        seLogService.insert(logType, logContent, getRemoteAddr(request), userName);
    }

    /**
     * returnMsg  返回操作信息
     *
     * @param response response
     * @param msg      msg
     */
    protected void returnMsg(HttpServletResponse response, Object msg) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(msg);
        } catch (Exception e) {
            LOG.warn("返回操作信息异常,msg={}", msg, e);
        }
    }

    /**
     * exportExcel exportExcel
     *
     * @param response     response
     * @param fileName     fileName
     * @param templateName templateName
     * @param dataList     dataList
     */
    protected void exportExcel(HttpServletResponse response, String fileName,
                               String templateName, List<T> dataList) {
        try {
            // 清空输出流
            response.reset();
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes(), "ISO-8859-1"));
            // 定义输出类型
            response.setContentType("application/msexcel");
            Map<String, Object> dataMap = new HashMap<String, Object>(16);
            dataMap.put("datas", dataList);
            // 取得输出流
            OutputStream outputStream = response.getOutputStream();
            ExcelUtil.buildExcelByTemplate("/exceltemplate/" + templateName, dataMap, outputStream);
        } catch (Exception e) {
            LOG.error("导出fileName={},templateName={},异常", fileName, templateName, e);
        }
    }
}
