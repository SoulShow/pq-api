package com.pq.api.type;

import com.google.common.collect.Maps;
import com.pq.api.exception.AppErrorCode;
import com.pq.api.utils.Values;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 错误对应关系
 *
 * @author liken
 * @date 15/3/13
 */
public enum Errors {

    Instance;

    //常见的错误，三位数，统一定义所有的系统级别的错误
    public static final Integer ServerUnavailable = 500;
    public static final Integer BadRequest = 400;
    public static final Integer NotFound = 404;
    public static final Integer MethodNotSuport = 405;
    public static final Integer UnkownError = 300;
    public static final Integer MediaTypeNotSupport = 460;
    public static final Integer InvalidRequest = 461;
    public static final Integer NotImplemented = 610;
    //所有业务错误都是4位数以上
    //授权相关
    public static final Integer LoginRequired = 1000;
    public static final Integer TokenInvalid = 1001;
    public static final Integer TokenValidateError = 1002;//用户名密码错误
    public static final Integer AccountIsLocked = 1003;//用户名密码错误
    //登录相关 账户相关
    public static final Integer CredentialNotMatch = 1020;//用户名密码错误
    public static final Integer PhoneNotMatches = 1021;
    public static final Integer UsernameAlreadyTaken = 1022;
    public static final Integer AccountNotExists = 1023;
    public static final Integer PasswordNotMatch = 1024;//登录密码错误
    public static final Integer RegisterClosed = 1025;
    public static final Integer IdNotFound = 1026;//身份证和手机号不匹配，请检查您的输入是否正确
    public static final Integer PasswordLevelLower = 1027;  // 密码等级太低
    public static final Integer CredentialNotMatchTimesRemain = 1028;//用户名密码错误
    public static final Integer MobileAlreadyRegistered = 1029;//手机号已经存在，不可注册
    public static final Integer UnknownClient = 1030;//未知的客户端类型
    public static final Integer RegisterFailed = 1031;//注册失败，请重试
    public static final Integer ParamsBindFailed = 2000;//参数绑定错误

    public static final Integer ServiceException = 3000;//业务不支持，这个是从Fund基金项目中抛出，所有的业务不支持都用这个消息
    public static final Integer UserAPIException = 3002;//跟用户中心操作失败，请稍后重试
    //验证码相关
    public static final Integer CodeRequestTooMuch = 4000;//验证码请求发送次数过多
    public static final Integer CodeErrorMoreThanAcceptable = 4001;// 一天内验证码输入错误次数大于3次
    public static final Integer CodeTooCloseWithLastTime = 4002;//距离上次发送验证码时间不足一分钟
    public static final Integer CodeExpired = 4003;//验证码失效，请重新获取输入
    public static final Integer CodeInputError = 4004;//验证码输入错误
    public static final Integer CodeCannotDelivery = 4005;//验证码发送失败，请稍后重试
    //
    public static final Integer HAVAE_NO_PERMISSION = 5001;//无权限
    public static final Integer GROUP_CREATE_OR_UPDATE_ERROR = 5002;//群组创建或更新失败
    public static final Integer IMG_IS_NULL = 5003;//图片为空
    public static final Integer TIME_PUSH_ERROR=5004;
    public static final Integer TIME_WATER_IMG_IS_NULL=5005;
    public static final Integer CHAT_LOG_IS_NULL=5006;//记录为空
    public static final Integer CHAT_CONTENT_IS_NULL=5007;//内容为空
    public static final Integer CHAT_ACCEPT_USER_ID_IS_NULL=5007;//接收人为空
    public static final Integer BLACK_EXIST_ERROR=5008;//已加入黑名单
    public static final Integer CONTENT_AND_IMG_IS_NULL=5009;//time文字图片都为空
    public static final Integer CONTENT_LENGTH_MORE=5010;//time文字>3000
    private Config errorConfs;

    /**
     * 构建函数，初始化
     */
    private Errors() {
        reset();
    }

    public static Errors getInstance() {
        return Instance;
    }

    /**
     * 有必要的话，可以再次调用，重新加载配置
     */
    public synchronized void reset() {
        this.errorConfs = null;

        //相当于命名空间，因为config默认继承了系统的设置 env
        //第一个load表明要加载的文件是什么，从第一个文件加载的选项，会跟系统环境重叠。
        //第二个是 文件中的
        Values values = Values.Instance;
        this.errorConfs = values.getConfig("openapi.errors");

    }

    public Map<String, String> list() {
        Map<String, String> errorDescs = Maps.newHashMap();

        for (Map.Entry<String, ConfigValue> entry : this.errorConfs.entrySet()) {

            errorDescs.put(entry.getKey(), entry.getValue().render());
        }

        return errorDescs;
    }

    /**
     * 根据错误代码获取 错误描述
     *
     * @param code
     * @return
     */
    public String getMessage(Integer code, Object... values) {
        return this.getMessage(code.toString(), values);
    }

    public boolean includes(String code) {
        return this.errorConfs.hasPath(code);
    }

    public boolean includes(Integer code) {
        return this.includes(code.toString());
    }

    /**
     * 输入错误代码，得出对应的消息
     *
     * @param code
     * @return
     * @throws IllegalArgumentException 如果输入的错误代码，没有找到对应项目的话，就会抛出
     */
    public String getMessage(String code, Object... values) {

        if (!errorConfs.hasPath(code)) {
            throw new IllegalArgumentException("未知的错误代码: " + code + "，请检查参数设置是否正确!");
        }

        String format = errorConfs.getString(code);
        if (values != null && values.length > 0) {
            return String.format(format, values);
        } else {
            return format;
        }
    }

    public AppErrorCode serviceError(ServiceErrorCode code) {
        String value = Values.getInstance().getValue(code);

        if (StringUtils.isNotBlank(value)) {

            if (code == ServiceErrorCode.ACCOUNT_DISABLED) {
                return new AppErrorCode(AccountIsLocked, value);
            } else {
                return new AppErrorCode(ServiceException, value);
            }

        } else {
            return new AppErrorCode(ServiceException, Values.getInstance().getValue(ServiceErrorCode.SERVER_ERROR));
        }

    }

}

