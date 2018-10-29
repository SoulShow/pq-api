package com.pq.api.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 用来测试的获取当前系统时间的类
 *
 * @author gogo
 */
public class SystemTime {

    private static TimeSource DEFAULT_SOURCE = new TimeSource() {
        @Override
        public long asMillis() {
            return System.currentTimeMillis();//返回当前时间
        }
    };

    private static TimeSource source = null;

    public static TimeSource getTimeSource() {
        return source != null ? source : DEFAULT_SOURCE;
    }

    public static synchronized void setTimeSource(TimeSource source) {
        SystemTime.source = source;
    }

    public static long asMillis() {
        return getTimeSource().asMillis();
    }

    public static Date asDate() {
        return new Date(asMillis());
    }

    public static Calendar asCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(asMillis());
        return cal;
    }

    public static Timestamp asTimestamp() {
        return new Timestamp(asMillis());
    }

    public static void reset() {
        setTimeSource(null);
    }

    /**
     * 根据小时分钟获得时间对象，如果不传递任何参数则返回当前时间
     *
     * @param time hourOfDay(24), minute, second
     * @return 时间对象
     * @author royall
     * @since 2012-3-29, 2012-04-12
     */
    public static Time asTime(int... time) {
        if (time.length == 0) {
            return new Time(asMillis());
        }
        Calendar instance = Calendar.getInstance(Locale.ROOT);
        instance.set(0, 0, 0, 0, 0, 0);
        if (time.length > 0) {
            instance.set(Calendar.HOUR_OF_DAY, time[0]);//hourOfDay
            if (time.length > 1) {
                instance.set(Calendar.MINUTE, time[1]);//minute
                if (time.length > 2) {
                    instance.set(Calendar.SECOND, time[2]);//second
                }
            }
        }
        return new Time(instance.getTimeInMillis());
    }

    /**
     * Time -> 13:30:00 -> 133000
     */
    public static int timeToInt(Time time) {
        if (time == null) {
            return 0;
        } else {
            return Integer.valueOf(time.toString().replace(":", "")).intValue();
        }
    }

    /**
     * 指定时间是否在指定的时间范围内
     *
     * @param nowTime   一个时间
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @author royall
     * @since 2012-4-12
     */
    public static boolean isInTimeRange(Time nowTime, Time beginTime, Time endTime) {

        int n = timeToInt(nowTime); //133000
        int b = timeToInt(beginTime); //93000
        int e = timeToInt(endTime);

        //如果开始时间大于结束时间
        if (b >= e) {
            //那么结束时间需要加上24小时来计算
            e = ((e / 10000 + 24) * 10000 + (e % 10000));
            //如果当前时间小于开始时间也需要加上24小时
            if (n < b) {
                n = ((n / 10000 + 24) * 10000 + (n % 10000));
            }
        }
        //判断当前时间是否在开始和结束时间范围内
        return (b <= n && n <= e);

    }
}
