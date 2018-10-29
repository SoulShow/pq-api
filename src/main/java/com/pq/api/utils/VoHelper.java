package com.pq.api.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * VO Helper类
 *
 * @author liken
 * @date 15/6/3
 */
public class VoHelper {

    /**
     * 加密身份证号码，兼容15、18位，带X的
     *
     * @return
     */
    public static String hideIdentification(String identification) {
        return hideByStar(identification, 3, 3, 9);
    }

    /**
     * 加密银行卡卡号信息
     *
     * @param card
     * @return
     */
    public static String hideBankCard(String card) {
        String hiddenCard = hideByStar(card, 4, 4, 10);
        //这里得到的是没有空格的版本，一般是 每四个一个空格

        StringBuilder beautyCard = new StringBuilder(24);

        for (int i = 0; i < hiddenCard.length(); i++) {
            beautyCard.append(hiddenCard.charAt(i));
            if ((i + 1) % 4 == 0 && (i + 1 < hiddenCard.length())) {
                beautyCard.append(' ');
            }
        }

        return beautyCard.toString();
    }

    /**
     * 加密名称
     *
     * @param name
     * @return
     */
    public static String hideName(String name) {

        return hideByStar(name, 1, 0, 2);
    }

    /**
     * 隐藏电话号码
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        return hideByStar(mobile, 3, 4, 8);
    }

    /**
     * 隐藏中间的字符串
     *
     * @param source    原始字符串
     * @param preIndex  前缀保留多少位
     * @param sufIndex  后缀保留多少位
     * @param minLength 字符串最小的长度是多少
     * @return
     */
    public static String hideByStar(String source, int preIndex, int sufIndex, int minLength) {
        if (StringUtils.isBlank(source)) {
            return "";
        }

        int repeat = source.length() - (preIndex + sufIndex);//星星重复几次
        if (repeat < 0) {
            throw new IllegalArgumentException("检查参数是否正确");
        }

        if (source.length() >= minLength) {
            String prefix = StringUtils.substring(source, 0, preIndex);
            String suffix = StringUtils.substring(source, source.length() - sufIndex, source.length());
            return String.format("%s%s%s", prefix, StringUtils.repeat('*', repeat), suffix);
        }

        return source;
    }

    /**
     * 只拿姓氏
     *
     * @param name
     * @return
     */
    public static String hideNameSolid(String name) {
        return StringUtils.join(StringUtils.substring(name, 0, 1), "**");
    }

    /**
     * 通过身份证号码判断是男是女，
     *
     * @param idCard
     * @return true 是男的， false 是女的
     */
    public static boolean getGenderByIdentity(String idCard) {
        if (StringUtils.isBlank(idCard) || idCard.length() < 10) {
            throw new IllegalArgumentException("身份证号码不符合格式");
        }

        char gender = idCard.charAt(idCard.length() - 2);

        return gender % 2 == 1;
    }
}

