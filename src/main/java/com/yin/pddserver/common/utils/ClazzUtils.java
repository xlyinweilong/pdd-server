package com.yin.pddserver.common.utils;

/**
 * 类方法
 *
 * @author yin.weilong
 * @date 2020.02.20
 */
public class ClazzUtils {

    /**
     * 获取类的名称
     *
     * @param clazz
     * @return
     */
    public static String getClassName(Class clazz) {
        return clazz.getName().replaceAll("\\.", ":");
    }

    /**
     * 获取redis的分布式锁
     *
     * @return
     */
    public static String getRedisLockKey(Class clazz, String methodName) {
        return ClazzUtils.getClassName(clazz) + ":" + methodName;
    }


}
