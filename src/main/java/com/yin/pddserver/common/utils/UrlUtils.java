package com.yin.pddserver.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * url处理工具
 *
 * @author yin.weilong
 * @date 2020.01.13
 */
public class UrlUtils {


    /**
     * URL解码
     *
     * @param str
     * @return
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getURLEncodeString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
