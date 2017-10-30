package com.elichn.pub.web.shiro.factorybean;

import com.elichn.pub.core.model.pub.pojo.security.SePublicPermission;
import com.elichn.pub.service.security.SePublicPermissionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: CustomShiroFilterFactoryBean</p>
 * <p>Description: CustomShiroFilterFactoryBean</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    private static transient final Logger LOG = LoggerFactory.getLogger(CustomShiroFilterFactoryBean.class);

    @Autowired
    private SePublicPermissionService sePublicPermissionService;

    public void setFilterChainDefinitions(String definitions) {
        List<SePublicPermission> list = sePublicPermissionService.selectBySystem(null);

        Map<String, String> section = new LinkedHashMap<String, String>();
        for (SePublicPermission permission : list) {
            section.put(permission.getResource(), permission.getFilter());
        }

        section.put("/**", "rescFilter");
        setFilterChainDefinitionMap(section);
    }

    /**
     * refreshFilter 刷新filterChain
     */
    public void refreshFilter() {
        this.setFilterChainDefinitions("");
        try {
            AbstractShiroFilter filter = (AbstractShiroFilter) this.getObject();
            FilterChainManager manager = createFilterChainManager();
            PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
            chainResolver.setFilterChainManager(manager);
            filter.setFilterChainResolver(chainResolver);
        } catch (Exception e) {
            LOG.info("{}", e);
        }
    }
}
