package com.yin.pddserver.common.utils;

import com.yin.pddserver.common.exceptions.ImportLineException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ImportListenerUtils {

    public static String getString(Map map, Integer i) {
        return StrUtils.toString(map.get(i));
    }

    public static Long getLong(Map map, Integer i) throws ImportLineException {
        try {
            return Long.parseLong(StrUtils.toString(map.get(i)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ImportLineException("sheet1页,第" + (i + 1) + "列,无法转化成数字");
        }
    }

    public static LocalDate getLocalDate(Map map, Integer i) throws ImportLineException {
        String s = StrUtils.toString(map.get(i));
        try {
            return DateUtils.parseLocalDateDefault(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ImportLineException("sheet1页,第" + (i + 1) + "列,无法转化成日期,请使用yyyy-MM-dd格式");
        }
    }

    public static BigDecimal getBigDecimal(Map map, Integer i) throws ImportLineException {
        try {
            String s = StrUtils.toString(map.get(i));
            return s != null ? new BigDecimal(s) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ImportLineException("sheet1页,第" + (i + 1) + "列,无法转化成数字");
        }
    }

    public static Boolean getBoolean(Map map, Integer i, Boolean defaultValue) throws ImportLineException {
        String s = StrUtils.toString(map.get(i));
        if (s == null) {
            return defaultValue;
        }
        if (s.equalsIgnoreCase("true") || s.equals("是") || s.equals("1")) {
            return true;
        } else if (s.equalsIgnoreCase("false") || s.equals("否") || s.equals("不是") || s.equals("0")) {
            return false;
        }
        throw new ImportLineException("sheet1页,第" + (i + 1) + "列,无法解析成布尔值,请使用TRUE,FALSE");
    }
}
