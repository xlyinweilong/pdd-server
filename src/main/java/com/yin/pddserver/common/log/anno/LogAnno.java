package com.yin.pddserver.common.log.anno;


import com.yin.pddserver.common.log.constant.LogTypeConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志
 *
 * @author yin.weilong
 * @date 2019.07.21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {

    String value() default "";

    String type() default LogTypeConstant.JSON;

}
