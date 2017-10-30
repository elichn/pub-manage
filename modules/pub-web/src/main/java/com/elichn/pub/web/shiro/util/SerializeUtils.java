package com.elichn.pub.web.shiro.util;


import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * <p>Title: SerializeUtils</p>
 * <p>Description: SerializeUtils</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class SerializeUtils {

    /**
     * isEmpty isEmpty
     *
     * @param data
     * @return boolean
     */
    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * deserialize 反序列化
     *
     * @param bytes bytes
     * @return Object
     */
    public static Object deserialize(byte[] bytes) {

        if (isEmpty(bytes)) {
            return null;
        }

        return SerializationUtils.deserialize(bytes);
    }

    /**
     * serialize 序列化
     *
     * @param object object
     * @return object
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }
        if (object instanceof Serializable) {
            return SerializationUtils.serialize((Serializable) object);
        } else {
            return new byte[0];
        }
    }
}

