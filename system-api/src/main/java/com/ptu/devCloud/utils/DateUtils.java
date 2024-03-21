package com.ptu.devCloud.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 获取指定月的开始时间
     * @return
     */
    public static Date getStartTimeOfCurrentMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
        setMinTimeOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * 获取指定月的结束时间
     * @return
     */
    public static Date getEndTimeOfCurrentMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        int maxMonthDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),maxMonthDay);
        setMaxTimeOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * 设置当天的开始时间
     * @param calendar
     */
    private static void setMinTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 设置当天的结束时间
     * @param calendar
     */
    private static void setMaxTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
