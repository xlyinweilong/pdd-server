package com.yin.pddserver.common.store;

/**
 * 线程存贮
 *
 * @author yin.weilong
 * @date 2019.09.02
 */
public class ThreadStore {

    /**
     * 当前线程的租户
     */
    private static ThreadLocal<String> tn = new ThreadLocal<>();

    /**
     * 当前线程的IP地址
     */
    public static ThreadLocal<String> ip = new ThreadLocal<>();

    public static String getTnId() {
        return tn.get();
    }

    public static void setTnId(String tnId) {
        tn.set(tnId);
    }

    public static String getIp() {
        return ip.get();
    }

    public static void setIp(String ipAdress) {
        ip.set(ipAdress);
    }

}
