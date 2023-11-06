package com.yin.pddserver.common.utils.calendar;

import java.util.Calendar;
import java.util.Date;

public class BirthdayUtils {

    /**
     * 获取最近的生日距离当前差几天（正数表示还有X天过，负数标书超过X天）
     *
     * @return
     */
    public static long getNearBirthday(Date birthday, boolean isLunar) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.setTime(birthday);
        birthdayCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));//当前年份
        long days1 = getDiff(calendar, isLunar ? toLunar(birthdayCalendar) : birthdayCalendar);
        birthdayCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);//当前上一年
        long days2 = getDiff(calendar, isLunar ? toLunar(birthdayCalendar) : birthdayCalendar);
        birthdayCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);//当前下一年
        long days3 = getDiff(calendar, isLunar ? toLunar(birthdayCalendar) : birthdayCalendar);
        long minAbs = Math.min(Math.min(Math.abs(days1), Math.abs(days2)), Math.abs(days3));
        if (minAbs == Math.abs(days1)) {
            return -days1;
        }
        if (minAbs == Math.abs(days2)) {
            return -days2;
        }
        if (minAbs == Math.abs(days3)) {
            return -days3;
        }
        return 0;
    }

    private static Calendar toLunar(Calendar calendar) {
        Lunar lunar = new Lunar(false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        Solar solar = LunarSolarConverter.lunarToSolar(lunar);
        calendar.set(Calendar.YEAR, solar.solarYear);
        calendar.set(Calendar.MONTH, solar.solarMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, solar.solarDay);
        return calendar;
    }

    private static long getDiff(Calendar start, Calendar end) {
        long diff = start.getTime().getTime() - end.getTime().getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

}
