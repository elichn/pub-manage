package com.elichn.pub.web.shiro.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>Title: ShiroSessionRepository</p>
 * <p>Description: ShiroSessionRepository</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public interface ShiroSessionRepository {

    /**
     * saveSession saveSession
     *
     * @param session session
     */
    void saveSession(Session session);

    /**
     * deleteSession deleteSession
     *
     * @param sessionId sessionId
     */
    void deleteSession(Serializable sessionId);

    /**
     * getSession getSession
     *
     * @param sessionId sessionId
     * @return Session
     */
    Session getSession(Serializable sessionId);

    /**
     * getAllSessions getAllSessions
     *
     * @return Collection<Session>
     */
    Collection<Session> getAllSessions();
}
