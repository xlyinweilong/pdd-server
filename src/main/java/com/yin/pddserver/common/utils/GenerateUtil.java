package com.yin.pddserver.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 生成工具类
 *
 * @author yin.weilong
 * @date 2018.12.14
 */
public class GenerateUtil {

    /**
     * 生成ID
     *
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成一个流水号
     *
     * @return
     */
    public static String createSerialNumber() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return date + RandomStringUtils.randomNumeric(4);
    }

    /**
     * 3位数补充0
     *
     * @param i
     * @return
     */
    public static String createAdd0(int i) {
        DecimalFormat df = new DecimalFormat("000");
        return df.format(i);
    }

    public static String createSerial16() {
        String date = digits32(Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        return date + RandomStringUtils.random(16 - date.length(), true, true);
    }

    // 32个字符，用来表示32进制
    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P',
            'Q', 'R', 'T', 'U', 'V', 'W',
            'X', 'Y'
    };

    /**
     * long类型转为32进制，指定了使用的字符，参考Long.toUnsignedString0
     *
     * @param val
     * @return
     */
    public static String digits32(long val) {
        // 32=2^5=二进制100000
        int shift = 5;
        // numberOfLeadingZeros 获取long值从高位连续为0的个数，比如val=0，则返回64
        // 此处mag=long值二进制减去高位0之后的长度
        int mag = Long.SIZE - Long.numberOfLeadingZeros(val);
        int len = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[len];
        do {
            // &31相当于%32
            buf[--len] = digits[((int) val) & 31];
            val >>>= shift;
        } while (val != 0 && len > 0);
        return new String(buf);
    }
}
