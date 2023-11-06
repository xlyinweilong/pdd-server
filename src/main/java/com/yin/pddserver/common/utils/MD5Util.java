package com.yin.pddserver.common.utils;

import org.springframework.util.DigestUtils;

/**
 * md5加密
 *
 * @author yin.weilong
 * @date 2019.09.02
 */
public class MD5Util {

    //盐，用于混交md5
    private static final String SLAT = "#zhisheng#";

    private static final String P_SLAT = "#wisdomyy#";

    /**
     * 生成md5
     *
     * @return
     */
    public static String md5(String str) {
        String base = P_SLAT + str + SLAT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
