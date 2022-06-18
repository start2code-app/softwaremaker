package com.company.softwaremaker.util;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {


    @Nullable
    public static Long getDaysBetweenDates(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return null;
        }
        if (org.apache.commons.lang3.time.DateUtils.isSameDay(firstDate, secondDate)) {
            return 0L;
        }
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static Date getLastDayOfPreviousMonth() {
        return getLastDayOfMonth(getPreviousMonth());
    }

    public static Date getNextWorkingDay() {
        Date nextDay = org.apache.commons.lang3.time.DateUtils.addDays(getCurrentDate(), 1);
        while (isWeekend(nextDay)) {
            nextDay = org.apache.commons.lang3.time.DateUtils.addDays(nextDay, 1);
        }
        return nextDay;
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (Calendar.SATURDAY == dayOfWeek) || (Calendar.SUNDAY == dayOfWeek);
    }

    public static String getYearMonth(Date date) {
        return new SimpleDateFormat("yyyyMM").format(date);
    }

    public static boolean isDateInCurrentMonth(Date date) {
        Date today = getCurrentDate();
        if (date == null) {
            return false;
        }
        return org.apache.commons.lang3.time.DateUtils.truncatedEquals(today, date, Calendar.MONTH);
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date getPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date previousMonth = calendar.getTime();
        return org.apache.commons.lang3.time.DateUtils.truncate(previousMonth, Calendar.MONTH);
    }


}
