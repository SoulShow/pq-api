package com.pq.api.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liutao
 */
public class PhoneAuthForm implements Serializable {
    private String account;
    private String verCode;
    private String deviceId;

    public PhoneAuthForm() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @NotNull(message = "请输入手机号")
    @NotBlank(message = "请输入手机号")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @NotNull(message = "请输入验证码")
    @NotBlank(message = "请输入验证码")
    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
