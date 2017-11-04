package com.elichn.pub.web.controller.security;

import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.model.pojo.security.SeLog;
import com.elichn.pub.service.security.SeLogService;
import com.elichn.pub.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: SeLogController</p>
 * <p>Description: SeLogController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/log")
public class SeLogController extends BaseController {

    private final static String PREFIX = "/security/log/";

    @Autowired
    private SeLogService seLogService;

    /**
     * loglist 日历列表
     *
     * @param model    model
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @param log      log
     */
    @RequestMapping(value = "/loglist")
    public void loglist(Model model,
                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                        SeLog log) {
        QueryBvo<SeLog> queryBvo = new QueryBvo<SeLog>(log, pageNo, pageSize);
        ResultBvo<SeLog> resultBvo = seLogService.getLogsList(queryBvo);
        model.addAttribute("list", resultBvo.getList());
        model.addAttribute("total", resultBvo.getTotal());
    }

    /**
     * logManager 日志管理入口页
     *
     * @return String
     */
    @RequestMapping(value = "/logManager")
    public String logManger() {
        return PREFIX + "logManager";
    }


    /**
     * exportExcel 导出统计数据
     *
     * @param response     response
     * @param fileName     fileName
     * @param templateName templateName
     */
    @RequestMapping(value = "exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "templateName", required = false) String templateName,
                            @RequestParam(value = "fileName", required = false) String fileName) {
        super.exportExcel(response, fileName, templateName, seLogService.getLogsList());
    }
}
