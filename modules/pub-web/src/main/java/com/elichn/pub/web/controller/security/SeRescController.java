package com.elichn.pub.web.controller.security;

import com.elichn.pub.core.constant.CommonConstats;
import com.elichn.pub.core.model.pojo.security.SeResc;
import com.elichn.pub.core.model.pojo.security.SeRescRoleKey;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.service.security.SeRoleRescService;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.annotation.MethodAfter;
import com.elichn.pub.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>Title: SeRescController</p>
 * <p>Description: SeRescController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/resc/")
public class SeRescController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(SeRescController.class);

    private final static String PREFIX = "/security/resc";

    @Autowired
    private SeRoleRescService seRoleRescService;
    @Autowired
    private SeRoleService seRoleService;
    @Autowired
    private SeUserService seUserService;

    /**
     * rescManager 资源管理入口页
     */
    @RequestMapping(value = "rescManager", method = RequestMethod.GET)
    public String rescManager() {
        return PREFIX + "/rescManager";
    }

    /**
     * addResc 添加资源
     *
     * @param model   model
     * @param request request
     * @param resc    resc
     */
    @RequestMapping(value = "addResc", method = RequestMethod.POST)
    public void addResc(Model model, HttpServletRequest request, SeResc resc) {
        boolean success = true;
        try {
            if (StringUtils.isBlank(resc.getResString())) {
                resc.setResString(null);
            }
            if (StringUtils.isBlank(resc.getShowUrl())) {
                resc.setShowUrl(null);
            }
            resc = seRoleRescService.insertResc(resc);
            // 记录日志
            super.writeLog(request, CommonConstats.ADD_RESOURCE, CommonConstats.ADD_RESOURCE
                    + "(资源名:" + resc.getName() + ",资源ID:" + resc.getId() + ")", super.getUserName());
        } catch (Exception e) {
            success = false;
            LOG.error("addResc error,", e);
        }
        model.addAttribute(MSG_KEY, success);
        model.addAttribute("currentResc", resc);
    }

    /**
     * updateResc 修改资源
     *
     * @param model   model
     * @param request request
     * @param resc    resc
     */
     @MethodAfter(methodDesc = "修改资源", log = "oldSeResc:$resc;newSeResc:" +
             "${model.asMap().get('currentResc')};结果:${model.asMap().get('msg')}")
    @RequestMapping(value = "updateResc", method = RequestMethod.POST)
    public void updateResc(Model model, HttpServletRequest request, SeResc resc) {
        boolean success = true;
        if (StringUtils.isBlank(resc.getResString())) {
            resc.setResString(null);
        }
        if (StringUtils.isBlank(resc.getShowUrl())) {
            resc.setShowUrl(null);
        }
        SeResc oldResc = seRoleRescService.selectRescById(resc.getId());
        // 信息封装
        String oldInfo = "原始信息:资源名:" + oldResc.getName() + ",资源ShowUrl:" + oldResc.getShowUrl()
                + ",资源ResString:" + oldResc.getResString() + ",资源FatherId:" + oldResc.getFatherId();
        String newInfo = "修改信息:资源名:" + resc.getName() + ",资源ShowUrl:" + resc.getShowUrl() + ",资源ResString:" + resc.getResString();
        try {
            seRoleRescService.updateResc(resc);
            // 记录日志
            super.writeLog(request, CommonConstats.UPDATE_RESOURCE, CommonConstats.UPDATE_RESOURCE
                    + "(资源名:" + resc.getDescn() + ",资源ID:" + resc.getId() + ")" + "<br>"
                    + "详细信息:<br>" + oldInfo + "<br>" + newInfo, super.getUserName());
        } catch (Exception e) {
            success = false;
            LOG.error("updateResc error,", e);
        }
        model.addAttribute(MSG_KEY, success);
        model.addAttribute("currentResc", resc);
    }

    /**
     * deleteResc 删除资源
     *
     * @param model   model
     * @param request request
     * @param rescId  rescId
     */
    @RequestMapping(value = "deleteResc", method = RequestMethod.GET)
    public void deleteResc(Model model, HttpServletRequest request, @RequestParam("rescId") Integer rescId) {
        boolean success = true;
        try {
            seRoleRescService.deleteResc(rescId);
            super.writeLog(request, CommonConstats.DELETE_RESOURCE,
                    CommonConstats.DELETE_RESOURCE + "(资源ID:" + rescId + ")", super.getUserName());
        } catch (Exception e) {
            success = false;
            LOG.error("deleteResc error,", e);
        }
        model.addAttribute(MSG_KEY, success);
    }

    /**
     * getResourceListByRole 根据当前用户初始化资源树
     *
     * @param model
     */
    @RequestMapping(value = "getResourceListByRole")
    public void getResourceListByRole(Model model) {
        List<SeResc> rescsList = this.getRescListByRole();
        model.addAttribute(LIST_KEY, rescsList);
    }

    /**
     * getRescListByRole 根据用户角色查找所有资源
     *
     * @return List<SeResc>
     */
    private List<SeResc> getRescListByRole() {
        // 获得当前用户
        SeUser currentUser = seUserService.selectByName(super.getUserName());
        // 根据当前用户活动当前用户的所有角色
        List<SeRole> currentRoles = seRoleService.selectRoleListByUserId(currentUser.getId());
        Set<Integer> rescSet = new HashSet<Integer>();
        // 获取当前角色的资源 id
        for (SeRole role : currentRoles) {
            List<SeRescRoleKey> keys = seRoleRescService.selectRescRoleKeyByRoleId(role.getId());
            for (SeRescRoleKey key : keys) {
                rescSet.add(key.getRescId());
            }
        }
        List<SeResc> list = seRoleRescService.selectRescListByIds(new ArrayList<Integer>(rescSet));
        Collections.sort(list, new Comparator<SeResc>() {
            @Override
            public int compare(SeResc o1, SeResc o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
        return list;
    }
}
