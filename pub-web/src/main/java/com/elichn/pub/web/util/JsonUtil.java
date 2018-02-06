package com.elichn.pub.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>Title: JsonUtil</p>
 * <p>Description: Json工具类</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/11/14
 */
public class JsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * writeValueAsString  obj转化为json字符串
     *
     * @param obj obj
     * @return exception
     * @throws IOException exception
     */
    public static String writeValueAsString(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * readValue json串转化为 <T>
     *
     * @param value value
     * @param t     t
     * @return <T>
     * @throws IOException exception
     */
    public static <T> T readValue(String value, Class<T> t) throws IOException {
        return mapper.readValue(value, t);
    }

    /**
     * toJson toJson
     *
     * @param object object
     * @return String
     */
    public static String toJson(Object object) {
        try {
            return writeValueAsString(object);
        } catch (Exception e) {
            LOG.error("toJson error,", e);
            return "{}";
        }
    }

    /**
     * jsonTo jsonTo
     *
     * @param value value
     * @param t     t
     * @return <T> T
     */
    public static <T> T jsonTo(String value, Class<T> t) {
        try {
            return readValue(value, t);
        } catch (Exception e) {
            LOG.error("jsonTo error,", e);
            return null;
        }
    }

}
