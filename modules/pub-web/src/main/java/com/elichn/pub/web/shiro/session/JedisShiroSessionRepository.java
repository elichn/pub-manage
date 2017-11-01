package com.elichn.pub.web.shiro.session;

import com.elichn.pub.web.shiro.util.SerializeUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: JedisShiroSessionRepository</p>
 * <p>Description: JedisShiroSessionRepository</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class JedisShiroSessionRepository implements ShiroSessionRepository {

    private static transient final Logger LOG = LoggerFactory.getLogger(JedisShiroSessionRepository.class);

    private String securityGroup = "";

    /**
     * saveSession
     */
    private final String REDIS_SHIRO_SESSION = "shiro-session:";

    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            LOG.error("session或者session id为空");
            return;
        }

        String key = getRedisSessionKey(session.getId());
        byte[] value = SerializeUtils.serialize(session);
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, session.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            LOG.error("id为空");
            return;
        }

        redisTemplate.delete(getRedisSessionKey(id));
    }

    @Override
    public Session getSession(Serializable id) {
        if (id == null) {
            LOG.error("id为空");
            return null;
        }

        byte[] value = redisTemplate.opsForValue().get(getRedisSessionKey(id));
        return (Session) SerializeUtils.deserialize(value);
    }

    @Override
    public Collection<Session> getAllSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<String> byteKeys = redisTemplate.keys(this.REDIS_SHIRO_SESSION + securityGroup + ":" + "*");

        if (byteKeys != null && byteKeys.size() > 0) {
            for (String bs : byteKeys) {
                Session s = (Session) SerializeUtils.deserialize(redisTemplate.opsForValue().get(bs));
                sessions.add(s);
            }
        }
        return sessions;
    }

    /**
     * getRedisSessionKey 获取redis中的session key
     *
     * @param sessionId sessionId
     * @return String
     */
    private String getRedisSessionKey(Serializable sessionId) {
        return this.REDIS_SHIRO_SESSION + securityGroup + ":" + sessionId;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }
}