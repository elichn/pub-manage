package com.elichn.pub.web.annotation;

import java.lang.annotation.*;

/**
 * <p>Title: MethodBefore</p>
 * <p>Description: MethodBefore</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodBefore {

    /**
     * methodDesc 方法描述
     *
     * @return String
     */
    String methodDesc();

    /**
     * log 日志
     *
     * @return String
     */
    String log() default "";
}
