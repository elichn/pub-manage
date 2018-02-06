package com.elichn.pub.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: Token</p>
 * <p>Description: Token</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/10/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * save 进入url时设置
     *
     * @return boolean
     */
    boolean save() default false;

    /**
     * remove 提交url时设置
     *
     * @return boolean
     */
    boolean remove() default false;

    /**
     * exclusion 提交url时设置
     * 后台验证的，设置还回model.addAttribute('key','value') ,此处设置key
     *
     * @return String
     */
    String exclusion() default "";

}
