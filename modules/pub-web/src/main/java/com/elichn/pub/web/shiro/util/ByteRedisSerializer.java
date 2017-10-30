package com.elichn.pub.web.shiro.util;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * <p>Title: ByteRedisSerializer</p>
 * <p>Description: ByteRedisSerializer</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
public class ByteRedisSerializer implements RedisSerializer<byte[]> {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    /**
     * isEmpty isEmpty
     *
     * @param data data
     * @return boolean
     */
    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * serialize serialize
     *
     * @param t t
     * @return byte[]
     * @throws SerializationException serializationException
     */
    public byte[] serialize(byte[] t) throws SerializationException {
        return t == null ? EMPTY_ARRAY : t;
    }

    /**
     * deserialize deserialize
     *
     * @param bytes bytes
     * @return byte[]
     * @throws SerializationException serializationException
     */
    public byte[] deserialize(byte[] bytes) throws SerializationException {
        return isEmpty(bytes) ? null : bytes;
    }
}
