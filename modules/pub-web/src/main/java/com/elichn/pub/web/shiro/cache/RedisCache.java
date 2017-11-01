package com.elichn.pub.web.shiro.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: RedisCache</p>
 * <p>Description: RedisCache</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);

    /**
     * The wrapped Jedis instance.
     */
    private RedisTemplate<K, V> redisTemplate;
    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro-redis-cache:";

    /**
     * RedisCache RedisCache通过一个JedisManager实例构造RedisCache
     *
     * @param redisTemplate redisTemplate
     * @param group         group
     */
    public RedisCache(RedisTemplate<K, V> redisTemplate, String group) {
        if (redisTemplate == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.redisTemplate = redisTemplate;

        keyPrefix += group + ":";
    }

    @Override
    public V get(K key) throws CacheException {
        LOG.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                return redisTemplate.opsForValue().get(keyPrefix + key);
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        LOG.debug("根据key从存储 key [" + key + "]");
        try {
            redisTemplate.opsForValue().set((K) (keyPrefix + key), value, 30, TimeUnit.MINUTES);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        LOG.debug("从redis中删除 key [" + key + "]");
        try {
            V previous = get(key);
            redisTemplate.delete((K) (keyPrefix + key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        LOG.debug("从redis中删除所有元素");
        try {
            redisTemplate.delete((K) (keyPrefix + "*"));
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            Long longSize = redisTemplate.opsForValue().size((K) (keyPrefix + "*"));
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    // @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        try {
            Set<K> keys = redisTemplate.keys((K) (keyPrefix + "*"));
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<K>();
                for (K key : keys) {
                    newKeys.add(key);
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        try {
            Set<K> keys = redisTemplate.keys((K) (keyPrefix + "*"));
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (K key : keys) {
                    V value = get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
}

