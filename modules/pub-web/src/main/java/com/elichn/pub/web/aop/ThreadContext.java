package com.elichn.pub.web.aop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: ThreadContext</p>
 * <p>Description: ThreadContext</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class ThreadContext {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = new ThreadLocal<Map<String, Object>>();

    /**
     * get get
     *
     * @param key key
     * @return Object
     */
    public static Object get(String key) {
        Map<String, Object> perThreadResources = CONTEXT.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    /**
     * put put
     *
     * @param key   key
     * @param value value
     */
    public static void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            remove(key);
            return;
        }
        ensureResourcesInitialized();
        CONTEXT.get().put(key, value);
    }

    /**
     * ensureResourcesInitialized
     */
    private static void ensureResourcesInitialized() {
        if (CONTEXT.get() == null) {
            CONTEXT.set(new HashMap<String, Object>());
        }
    }

    /**
     * remove remove
     *
     * @param key key
     * @return Object
     */
    public static Object remove(String key) {
        Map<String, Object> perThreadResources = CONTEXT.get();
        return perThreadResources != null ? perThreadResources.remove(key) : null;
    }

    /**
     * getContext getContext
     *
     * @return Map<String, Object>
     */
    public static Map<String, Object> getContext() {
        if (CONTEXT.get() == null) {
            return Collections.emptyMap();
        } else {
            return new HashMap<String, Object>(CONTEXT.get());
        }
    }
}
