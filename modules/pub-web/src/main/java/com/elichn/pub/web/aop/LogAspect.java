package com.elichn.pub.web.aop;


import com.elichn.pub.web.annotation.CrudMethodAfter;
import com.elichn.pub.web.annotation.CrudMethodBefore;
import com.elichn.pub.web.log.LogWriter;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>Title: LogAspect</p>
 * <p>Description: LogAspect</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogWriter logWriter;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("@annotation(com.elichn.pub.web.annotation.CrudMethodBefore)")
    public void before() {
    }

    @Pointcut("@annotation(com.elichn.pub.web.annotation.CrudMethodAfter)")
    public void after() {
    }

    @Before("before()")
    public void doBefore(JoinPoint jp) {
        try {
            MethodSignature ms = (MethodSignature) jp.getSignature();
            Method method = ms.getMethod();
            CrudMethodBefore before = method.getAnnotation(CrudMethodBefore.class);
            this.writeLog(jp, before.methodDesc(), before.log());
        } catch (Exception e) {
            LOG.warn("before AOP异常,", e);
        }
    }

    @AfterReturning("after()")
    public void doAfter(JoinPoint jp) {
        try {
            MethodSignature ms = (MethodSignature) jp.getSignature();
            Method method = ms.getMethod();
            CrudMethodAfter after = method.getAnnotation(CrudMethodAfter.class);
            this.writeLog(jp, after.methodDesc(), after.log());
        } catch (Exception e) {
            LOG.warn("after AOP异常,", e);
        }
    }

    /**
     * writeLog writeLog
     *
     * @param jp          jp
     * @param methodDesc  方法描述
     * @param logTemplate logTemplate
     */
    private void writeLog(JoinPoint jp, String methodDesc, String logTemplate) {
        StringWriter sw = null;
        try {
            Context context = new VelocityContext();
            String[] argNames = ((MethodSignature) jp.getSignature()).getParameterNames();
            Object[] args = jp.getArgs();
            int len = args.length;
            for (int i = 0; i < len; i++) {
                context.put(argNames[i], args[i]);
            }
            Map<String, Object> map = ThreadContext.getContext();
            for (Map.Entry<String, Object> e : map.entrySet()) {
                context.put(e.getKey(), e.getValue());
            }
            ThreadContext.remove();
            sw = new StringWriter();
            Velocity.evaluate(context, sw, "LogAspect", logTemplate);
            logWriter.write(methodDesc, sw.toString(), getRemoteAddr(request), getUserName());
        } catch (Exception e) {
            LOG.warn("日志记录异常,", e);
        } finally {
            IOUtils.closeQuietly(sw);
        }
    }

    /**
     * getRemoteAddr 获取ip
     *
     * @param request request
     * @return String
     */
    private String getRemoteAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if (remoteAddr == null || "".equals(remoteAddr)) {
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
     * getUserName 获得当前登录的用户名
     *
     * @return String
     */
    public String getUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        return (String) currentUser.getPrincipal();
    }
}
