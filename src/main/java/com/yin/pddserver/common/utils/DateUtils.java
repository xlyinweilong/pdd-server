package com.yin.pddserver.common.utils;

import com.yin.pddserver.common.exceptions.MessageException;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 时间工具
 *
 * @author yin.weilong
 * @date 2019.08.10
 */
public class DateUtils {

    //日期比较方法
    public static int compareDate(Date first, Date second) {
        return first.compareTo(second);
    }

    /**
     * 增加小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, Integer second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date addDay(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date addYear(Date date, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }


    /**
     * 获取一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTime();
    }

    public static Date getStartOfDay(LocalDate date) {
        return getStartOfDay(asDate(date));
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取一个月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(calendar.getTime());
    }

    public static Date getStartOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate monday = DateUtils.asLocalDate(date).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return DateUtils.asDate(monday);
    }

    public static Date getEndOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate sunday = DateUtils.asLocalDate(date).plusDays(7L).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return DateUtils.getEndOfDay(DateUtils.asDate(sunday));
    }

    public static Date getStartOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(calendar.getTime());
    }


    /**
     * 获取一天的结束时间
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getEndOfDay(LocalDate date) {
        return getEndOfDay(asDate(date));
    }

    public static Date getEndOfMonth(Date date) {
        return addSecond(addMonth(getStartOfMonth(date), 1), -1);
    }

    public static Date getEndOfYear(Date date) {
        return addSecond(addYear(getStartOfYear(date), 1), -1);
    }

    /**
     * 获取现在时间
     *
     * @return
     */
    public static String getNowStr() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(now);
    }

    public static String getNowSecondStr() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(now);
    }

    public static String getNowSecondStrNoDay() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(now);
    }

    /**
     * 格式化数据
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * date转localDate
     *
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate parseLocalDateDefaultNoException(String str) {
        try {
            return parseLocalDateDefault(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate parseLocalDateDefault(String str) throws MessageException {
        if (null == str) {
            return null;
        }
        if (isInt(str)) {
            Calendar c = new GregorianCalendar(1900, 0, -1);
            c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(str));
            return LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        }
        String pattern = getLocalDatePattern(str);
        try {
            return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            throw new MessageException("无法转化成日期,请使用yyyy-MM-dd格式");
        }
    }

    private static final Pattern PATTERN = Pattern.compile("0|([-]?[1-9][0-9]*)");

    private static boolean isInt(String str) {
        return PATTERN.matcher(str).matches();
    }

    private static String getLocalDatePattern(String str) throws MessageException {
        if (Pattern.compile("^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$").matcher(str).matches()) {
            return "MM/dd/yyyy";
        }
        if (Pattern.compile("^[0-9]{1,2}[/][0-9]{1,2}[/][0-9]{4}$").matcher(str).matches()) {
            return "M/d/yyyy";
        }
        if (Pattern.compile("^[0-9]{4}[/][0-9]{2}[/][0-9]{1}$").matcher(str).matches()) {
            return "yyyy/MM/d";
        }
        if (Pattern.compile("^[0-9]{4}[/][0-9]{1,2}[/][0-9]{1,2}$").matcher(str).matches()) {
            return "yyyy/M/d";
        }
        if (Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$").matcher(str).matches()) {
            return "yyyy-MM-dd";
        }
        if (Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$").matcher(str).matches()) {
            return "yyyy-M-d";
        }
        throw new MessageException("无法找到表达式");
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return date.format(fmt);
    }

    public static String formatLocalDate(LocalDate date) {
        return formatLocalDate(date, "yyyy-MM-dd");
    }

    public static int getDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    return 29;
                }
                return 28;
        }
        return 0;
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

}
