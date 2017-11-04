package com.elichn.pub.web.controller.security;

import com.elichn.pub.core.constant.CommonConstats;
import com.elichn.pub.core.model.bvo.QueryBvo;
import com.elichn.pub.core.model.bvo.ResultBvo;
import com.elichn.pub.core.model.bvo.SeRoleTreeBvo;
import com.elichn.pub.core.model.pojo.security.SeHomePageNotice;
import com.elichn.pub.core.model.pojo.security.SeRole;
import com.elichn.pub.core.model.pojo.security.SeUser;
import com.elichn.pub.service.security.SeHomePageNoticeService;
import com.elichn.pub.service.security.SeRoleService;
import com.elichn.pub.service.security.SeUserService;
import com.elichn.pub.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Title: SeHomePageNoticeController</p>
 * <p>Description: SeHomePageNoticeController</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Controller
@RequestMapping(value = "/homePageNotice")
public class SeHomePageNoticeController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(SeHomePageNoticeController.class);

    private final static String PREFIX = "/security/notice";

    @Autowired
    private SeHomePageNoticeService seHomePageNoticeService;
    @Autowired
    private SeUserService seUserService;
    @Autowired
    private SeRoleService seRoleService;

    /**
     * view view
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("style", SecurityUtils.getSubject().getSession().getAttribute(CommonConstats.STYLE));
        return PREFIX + "/view";
    }

    /**
     * list list
     *
     * @param model    model
     * @param queryBvo queryBvo
     * @param pageNo   queryBvo
     * @param pageSize pageSize
     */
    @RequestMapping(value = "/list")
    public void list(Model model, SeHomePageNotice queryBvo,
                     @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                     @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        QueryBvo<SeHomePageNotice> qb = new QueryBvo<SeHomePageNotice>(queryBvo, pageNo, pageSize);
        ResultBvo<SeHomePageNotice> resultBvo = seHomePageNoticeService.getHomePageNoticeList(qb);
        model.addAttribute("datas", resultBvo);
    }

    /**
     * add add
     *
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return PREFIX + "/add";
    }

    /**
     * add add
     *
     * @param model   model
     * @param hn      hn
     * @param roleIds roleIds
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(Model model, SeHomePageNotice hn, int[] roleIds) {
        if (hn.getType() == 1) {
            String welcomePath = "/welcome";
            if (StringUtils.isBlank(hn.getUrl()) || welcomePath.equals(hn.getUrl())) {
                model.addAttribute("msg", INVALID_PARAM);
                return;
            }
        } else {
            if (StringUtils.isBlank(hn.getContent())) {
                model.addAttribute("msg", INVALID_PARAM);
                return;
            }
        }
        try {
            seHomePageNoticeService.insert(hn, roleIds);
            model.addAttribute("msg", SUCCESS);
        } catch (Exception e) {
            LOG.error("{}", e);
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * edit edit
     *
     * @param model model
     * @param hn    hn
     */
    @RequestMapping(value = "/edit")
    public void edit(Model model, SeHomePageNotice hn) {
        if (hn.getType() == 1) {
            Assert.isTrue(StringUtils.isNotBlank(hn.getUrl()));
        } else {
            Assert.isTrue(StringUtils.isNotBlank(hn.getContent()));
        }
        try {
            seHomePageNoticeService.updateByPrimaryKeyWithBLOBs(hn);
            model.addAttribute("msg", SUCCESS);
        } catch (Exception e) {
            LOG.error("{}", e);
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * updateRoleNotice updateRoleNotice
     *
     * @param model    model
     * @param noticeId noticeId
     * @param roleId   roleId
     */
    @RequestMapping(value = "/updateRoleNotice")
    public void updateRoleNotice(Model model,
                                 @RequestParam("noticeId") int noticeId,
                                 @RequestParam("roleId") Integer[] roleId) {
        try {
            seHomePageNoticeService.updateRoleNotice(noticeId, Arrays.asList(roleId));
            model.addAttribute("msg", SUCCESS);
        } catch (Exception e) {
            LOG.error("{}", e);
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * updateAsNew updateAsNew
     *
     * @param model model
     * @param id    id
     */
    @RequestMapping(value = "/updateAsNew")
    public void updateAsNew(Model model,
                            @RequestParam("id") int id) {
        try {
            int re = seHomePageNoticeService.updateAsNew(id);
            if (re > 0) {
                model.addAttribute("msg", SUCCESS);
            } else {
                model.addAttribute("msg", FAIL);
            }
        } catch (Exception e) {
            LOG.error("{}", e);
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * getNotice getNotice
     *
     * @param model model
     */
    @RequestMapping(value = "/getNotice", method = RequestMethod.GET)
    public void getNotice(Model model) {
        SeUser user = getCurrentUser();
        if (user != null) {
            SeHomePageNotice hn = seHomePageNoticeService.getHomePageNoticeByUser(user.getId());
            model.addAttribute("hn", hn);
        }
    }

    /**
     * changeStatus changeStatus
     *
     * @param model  model
     * @param id     id
     * @param status id
     */
    @RequestMapping(value = "/changeStatus")
    public void changeStatus(Model model,
                             @RequestParam("id") Integer id,
                             @RequestParam("status") Integer status) {
        int re = seHomePageNoticeService.updateStatus(id, status);
        if (re > 0) {
            model.addAttribute("msg", SUCCESS);
        } else {
            model.addAttribute("msg", FAIL);
        }
    }

    /**
     * getRelationRole getRelationRole
     *
     * @param model model
     * @param hnId  hnId
     */
    @RequestMapping(value = "/getRelationRole")
    public void getRelationRole(Model model,
                                @RequestParam("hnId") Integer hnId) {
        String userName = getUserName();
        if (userName != null) {
            SeUser user = seUserService.selectByName(userName);
            if (user != null) {
                List<SeRole> list = seRoleService.selectRolesByUser(user.getId());
                List<Integer> roles = seHomePageNoticeService.getRelationRole(hnId);

                List<SeRoleTreeBvo> bvos = new ArrayList<SeRoleTreeBvo>();
                for (SeRole r : list) {
                    SeRoleTreeBvo bvo = SeRoleTreeBvo.copyFromRole(r);
                    if (roles.contains(r.getId())) {
                        bvo.setChecked(true);
                    }
                    bvos.add(bvo);
                }
                model.addAttribute("list", bvos);
            }
        }
    }
}
