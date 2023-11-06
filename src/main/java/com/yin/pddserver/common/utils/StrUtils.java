package com.yin.pddserver.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 导工具
 *
 * @author yin.weilong
 * @date 2019.09.15
 */
@Slf4j
public class StrUtils {

    public static String toString(Object str) {
        return str == null ? null : StringUtils.trimToNull(str.toString());
    }

    public static String toUpperFristChar(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 首字母转小写、驼峰转下划线
     *
     * @param s 待转换的字符串
     * @return 转换结果
     */
    public static String capitalToInitial(String s) {
        StringBuilder sb = new StringBuilder();
        if (s == null || s.trim().isEmpty()) {
            return null;
        }
        if (s.length() <= 1) {
            return sb.append(s).toString().toLowerCase();
        }
        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            if (Character.isUpperCase(charAt)) {
                sb.append("_");
                sb.append(Character.toLowerCase(charAt));
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
