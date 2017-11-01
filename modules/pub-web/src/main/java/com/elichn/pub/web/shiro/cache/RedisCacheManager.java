package com.elichn.pub.web.shiro.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Title: RedisCacheManager</p>
 * <p>Description: RedisCacheManager</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class RedisCacheManager implements CacheManager {

    private static final Logger LOG = LoggerFactory.getLogger(RedisCacheManager.class);

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Autowired
    private RedisTemplate redisTemplate;

    private String securityGroup = "";

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        LOG.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache c = caches.get(name);
        if (c == null) {
            // create a new cache instance
            c = new RedisCache<K, V>(redisTemplate, securityGroup);
            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }
}

