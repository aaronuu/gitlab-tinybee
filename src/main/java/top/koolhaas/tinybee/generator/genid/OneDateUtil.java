package top.koolhaas.tinybee.generator.genid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * vcc日期工具类
 *
 * @author lijunjie
 * @version $Id: VccDateUtil.java, v 0.1 2015-11-09 下午3:16 lijunjie Exp $
 */
public class OneDateUtil {

    /**
     * yyyyMMdd日期格式化器
     */
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "yyyyMMdd");
        }
    };

    /**
     * yyMMdd日期格式化器
     */
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_YEAR_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "yyMMdd");
        }
    };

    /**
     * yyyy-MM-dd HH:mm:ss,SSS日期格式化器
     */
    private static final ThreadLocal<SimpleDateFormat> ALL_DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss,SSS");
        }
    };

    /**
     * yyyyMMddHHmmss日期格式化器
     */
    private static final ThreadLocal<SimpleDateFormat> LONG_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "yyyyMMddHHmmss");
        }
    };

    /**
     * 日期格式化(yyyyMMdd)为字符串
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatSimple(Date date) {
        return SIMPLE_DATE_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 日期格式化(yyMMdd)为字符串
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatSimpleYear(Date date) {
        return SIMPLE_YEAR_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 字符串格式化(yyyyMMdd)为日期对象
     *
     * @param str 字符串
     * @return 日期对象
     */
    public static Date parseSimple(String str) {
        try {
            return SIMPLE_DATE_FORMAT_THREAD_LOCAL.get().parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Parse date exception", e);
        }
    }

    /**
     * 日期格式化(yyyy-MM-dd HH:mm:ss,SSS)为字符串
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatAllTime(Date date) {
        return ALL_DATE_TIME_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 日期格式化(yyyyMMddHHmmss)为字符串
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatLong(Date date) {
        return LONG_TIME_FORMAT_THREAD_LOCAL.get().format(date);
    }

    /**
     * 添加日期秒
     *
     * @param date   原始日期
     * @param sconds 秒
     * @return 目标日期
     */
    public static Date addSconds(Date date, int sconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, sconds);
        return calendar.getTime();
    }

    /**
     * 增加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 获取日期开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 是否前者比后者小
     *
     * @param lessDate  较小的时间
     * @param largeDate 较大的时间
     * @return true lessDate小于largeDate false lessDate大于等于largeDate
     */
    public static boolean isBefore(final Date lessDate, final Date largeDate) {
        return lessDate.getTime() < largeDate.getTime();
    }

    /**
     * 是否前者大于后者
     *
     * @param leftDate  前者
     * @param rightDate 后者
     * @return true lessDate大于等于largeDate false  lessDate小于largeDate
     */
    public static boolean isAfter(final Date leftDate, final Date rightDate) {
        return leftDate.getTime() > rightDate.getTime();
    }

    /**
     * 是否前者小于等于后者
     *
     * @param lessDate  较小的时间
     * @param largeDate 较大的时间
     * @return true lessDate小于largeDate false lessDate大于等于largeDate
     */
    public static boolean isBeforeOrEqual(final Date lessDate, final Date largeDate) {
        return lessDate.getTime() <= largeDate.getTime();
    }
}
