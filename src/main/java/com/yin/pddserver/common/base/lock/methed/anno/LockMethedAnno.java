package com.yin.pddserver.common.base.lock.methed.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法锁
 *
 * @author yin.weilong
 * @date 2019.07.21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockMethedAnno {

    long seconds() default 45L;

    String key() default "";

}
