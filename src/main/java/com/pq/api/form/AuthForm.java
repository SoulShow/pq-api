package com.pq.api.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liutao
 */
public class AuthForm implements Serializable {
    private static final long serialVersionUID = -4745983525768728758L;
    private String account;
    private String password;
    private String deviceId;

    public AuthForm() {
    }

    public AuthForm(String account, String password) {
        super();
        this.account = account;
        this.password = password;
    }

    public AuthForm(String account, String password, String deviceId) {
        super();
        this.account = account;
        this.password = password;
        this.deviceId = deviceId;
    }



    @NotNull(message = "密码必须填写")
    @NotBlank(message = "密码必须不能为空")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @NotNull(message = "用户名必须填写")
    @NotBlank(message = "用户名必须不能为空")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
