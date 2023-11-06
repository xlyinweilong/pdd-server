package com.yin.pddserver.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 工具
 *
 * @author yin.weilong
 * @date 2019.09.15
 */
@Slf4j
public class BigDecimalUtils {

    public static BigDecimal multiply(BigDecimal a, Long b) {
        if (a == null || b == null) {
            return null;
        }
        return a.multiply(BigDecimal.valueOf(b));
    }

    public static BigDecimal multiply(BigDecimal a, Integer b) {
        if (a == null || b == null) {
            return null;
        }
        return a.multiply(BigDecimal.valueOf(b));
    }


    public static String getPlainString(BigDecimal b) {
        if (b == null) {
            return null;
        }
        return b.toPlainString();
    }

    public static RoundingMode getRoundingMode(String decimalType) {
        //round,ceil,floor
        if (decimalType.equals("ceil")) {
            return RoundingMode.UP;
        } else if (decimalType.equals("floor")) {
            return RoundingMode.DOWN;
        } else {
            return RoundingMode.HALF_UP;
        }
    }
}
