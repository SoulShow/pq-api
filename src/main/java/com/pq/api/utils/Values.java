package com.pq.api.utils;

import com.google.common.base.Preconditions;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liken
 * @date 15/3/13
 */
@Component
public enum Values {
    Instance;

    /**
     * 全局的文字配置，就是通过一个KEY配置
     */
    private static final String GLOBAL_TEXT = "global.text";
    private Config valuesConfig;

    private Config globalConfig;

    private Values() {

        //相当于命名空间，因为config默认继承了系统的设置 env
        //第一个load表明要加载的文件是什么，从第一个文件加载的选项，会跟系统环境重叠。
        //第二个是 文件中的
        valuesConfig = ConfigFactory.load("values");
        this.globalConfig = valuesConfig.getConfig(GLOBAL_TEXT);
    }

    /**
     * 工厂方法
     *
     * @return
     */
    public static Values getInstance() {
        return Instance;
    }

    /**
     * 通过命名空间，获取values文件中的子配置
     *
     * @param namespace
     * @return
     */
    public Config getConfig(String namespace) {

        if (!valuesConfig.hasPath(namespace)) {
            throw new IllegalArgumentException("无法根据命名空间：" + namespace + " 找到配置文件");
        }

        return valuesConfig.getConfig(namespace);
    }

    /**
     * 根据枚举获取相应的配置，默认按照 packge.classname 作为namespace，
     * 然后具体的值，作为key
     *
     * @param enumEntry
     * @return
     */
    public String getValue(Enum<?> enumEntry) {

        String value = getValueWithEnum(enumEntry);

        return value == null ? "" : value;
    }

    private String getValueWithEnum(Enum<?> enumEntry) {
        Config enumConfig = getEnumContainer(enumEntry);

        if (enumConfig.hasPath(enumEntry.name())) {
            return enumConfig.getString(enumEntry.name());
        } else {
            return null;
        }
    }

    /**
     * 通过枚举 获取配置对象
     *
     * @param enumEntry
     * @return
     */
    public Config getEnumConfig(Enum<?> enumEntry) {
        Config enumConfig = getEnumContainer(enumEntry);

        if (enumConfig.hasPath(enumEntry.name())) {
            return enumConfig.getConfig(enumEntry.name());
        } else {
            return null;
        }
    }

    private Config getEnumContainer(Enum<?> enumEntry) {
        String namespace = enumEntry.getClass().getName();

        namespace = namespace.indexOf('$') > -1 ? quote(namespace).intern() : namespace;

        Config enumConfig = getConfig(namespace);
        return enumConfig;
    }

    public String quote(String key) {
        return String.format("\"%s\"", key);
    }

    /**
     * 根据枚举获取相应的配置，默认按照 packge.classname 作为namespace，
     * 然后具体的值，作为key
     * 跟
     *
     * @param enumEntry
     * @return
     * @throws {@link IllegalArgumentException} 如果无法找到对应的KEY，抛出运行时异常
     */
    public String getValueStrictly(Enum<?> enumEntry) {

        String value = getValueWithEnum(enumEntry);

        if (value == null) {
            throw new IllegalArgumentException("不存在这个配置: " + enumEntry.getClass().getName() + "#" + enumEntry.name());
        } else {
            return value;
        }

    }

//    /**
//     * 获取普通的提示文字
//     * @return
//     */
//    public String getTextValue(String textKey) {
//        return getConfig(GLOBAL_TEXT).getString(textKey);
//    }


    /**
     * 获取普通的提示文字，并且进行变量替换
     *
     * @param textKey
     * @return
     */
    public String getTextValue(String textKey, Object... args) {
        String format = globalConfig.getString(textKey);
        if (args != null && args.length > 0) {
            return String.format(format, args);
        } else {
            return format;
        }
    }

    public Number getNumber(String path) {
        return globalConfig.getNumber(path);
    }

    public int getInt(String path) {
        return globalConfig.getInt(path);
    }

    public long getLong(String path) {
        return globalConfig.getLong(path);
    }

    public Long getMilliseconds(String path) {
        return globalConfig.getDuration(path, TimeUnit.MILLISECONDS);
    }

    public Config getGlobalConfig() {
        return globalConfig;
    }

    /**
     * 格式化时间
     *
     * @param key 规则
     * @param date
     * @return
     */
    public String getFormatedDate(String key, Date date) {
        Preconditions.checkNotNull(date, "时间不允许为空");

        String pattern = getTextValue(key);
        Preconditions.checkArgument(StringUtils.isNotBlank(pattern), "时间格式必须不能为空字符串");

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

}

