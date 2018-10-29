package com.pq.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author gogo
 * @history
 */
public abstract class Utils {

    /**
     * 异常发生的时候，保存的KEY值
     */

    public static final String EMPTY_NAMESPACE = "";//空的命名空间
    public static final String HTTP_SEPERATOR = "://";
    public static final String EMAIL_ADDRESS = "%s <%s>";
    private final static Logger logger = LoggerFactory.getLogger(Utils.class);
    //定义格式化 字节数的一些静态变量
    private static final String[] DECIMAL_PREFIXS = {"k", "M", "G", "T", "P", "E"};
    private static final String[] BINARY_PREFIXS = {"Ki", "Mi", "Gi", "Ti", "Pi", "Ei"};
    private static final String READBLE_BYTECOUNT_FORMAT = "%.2f %sB";


    /**
     * 返回便于人类阅读的字节数字符串
     * 参考 <a href="http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java/3758880#3758880"
     * >How to convert byte size into human readable format in java?</a> 而写
     * <p/>
     * <pre>
     * Decimal 十进制                   Binary 二进制
     * Value   SI                    Value   IEC JEDEC
     * 1000     k   kilo             1024     Ki  kibi    K   kilo
     * 1000^2   M   mega             1024^2   Mi  mebi    M   mega
     * 1000^3   G   giga             1024^3   Gi  gibi    G   giga
     * 1000^4   T   tera             1024^4   Ti  tebi
     * 1000^5   P   peta             1024^5   Pi  pebi
     * 1000^6   E   exa              1024^6   Ei  exbi
     * 1000^7   Z   zetta            1024^7   Zi  zebi
     * 1000^8   Y   yotta            1024^8   Yi  yobi
     * </pre>
     *
     * @param bytes 字节数
     * @param si    是否使用 十进制的显示
     * @return
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));//获得针对unit单位的幂是多少

        String pre = si ? DECIMAL_PREFIXS[exp - 1] : BINARY_PREFIXS[exp - 1];
        return String.format(READBLE_BYTECOUNT_FORMAT, bytes / Math.pow(unit, exp), pre);
    }

    /**
     * 返回便于人类阅读的字节数字符串，默认采用 二进制显示
     *
     * @param bytes 字节数
     * @return
     */
    public static String humanReadableByteCount(long bytes) {
        return humanReadableByteCount(bytes, false);
    }

    /**
     * 获取两个日期中的 所有月份，结果会 包含从开始 到 结束的所有，
     * 比如说 start 跟 end 差一个月，结果会有2个
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param order 排序方式，默认是按照时间降序
     * @return
     */
    public static List<Date> getMonthDateBetween(Date start, Date end, Order order) {
        List<Date> dates = new ArrayList<Date>();

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        switch (order) {
            case asc:
                calendar.setTime(start);

                while (calendar.getTime().before(end)) {
                    Date temp = calendar.getTime();
                    dates.add(temp);
                    calendar.add(Calendar.MONTH, 1);
                }

                dates.add(end);
                break;

            default:

                calendar.setTime(end);
                while (calendar.getTime().after(start)) {
                    Date temp = calendar.getTime();
                    dates.add(temp);
                    calendar.add(Calendar.MONTH, -1);
                }

                dates.add(start);
                break;
        }
        return dates;
    }

    /**
     * 获取两个日期中的 所有月份，结果会 包含从开始 到 结束的所有，
     * 比如说 start 跟 end 差一个月，结果会有2个
     * 排序方式，默认是按照时间降序
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<Date> getMonthDateBetween(Date start, Date end) {
        return getMonthDateBetween(start, end, Order.desc);
    }

    /**
     * 是否为中文字，不包括符号
     *
     * @param c
     * @return
     */
    public static boolean isChineseWord(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                ) {
            return true;
        }
        return false;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChineseWord(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取星期几
     *
     * @param date
     * @return
     */
    public static char getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case 1:
                return '日';
            case 2:
                return '一';
            case 3:
                return '二';
            case 4:
                return '三';
            case 5:
                return '四';
            case 6:
                return '五';
            default:
                return '六';
        }
    }


}
