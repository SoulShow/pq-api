package com.pq.api.type;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 解析苹果的机器型号
 *
 * @author liken
 * @date 15/3/13
 */
public enum Machine {

    INSTANCE;

    private Config appleDevices;

    /**
     * 构建函数，初始化
     */
    private Machine() {
        reset();
    }

    public static Machine getInstance() {
        return INSTANCE;
    }

    /**
     * 有必要的话，可以再次调用，重新加载配置
     */
    public synchronized void reset() {
        this.appleDevices = null;

        //相当于命名空间，因为config默认继承了系统的设置 env
        //第一个load表明要加载的文件是什么，从第一个文件加载的选项，会跟系统环境重叠。
        //第二个是 文件中的
        this.appleDevices = ConfigFactory.load("apple-machines").getConfig("apple.devices");

    }

    /**
     * 是否拥有该机器代码
     *
     * @param internalName
     * @return
     */
    public boolean hasName(String internalName) {
        //因为格式有问题，如果查询的字符有逗号的话，会报错
        String wrap = String.format("\"%s\"", internalName);
        return appleDevices.hasPath(wrap);
    }

    /**
     * 根据苹果代号，获取苹果设备名称
     *
     * @param internalName
     * @return
     */
    public String getMachineName(String internalName) {
        String wrap = String.format("\"%s\"", internalName);

        if (appleDevices.hasPath(wrap)) {// "iPhone5,2"
            return appleDevices.getString(wrap);
        } else {
            return "Unkown";
        }
    }
}

