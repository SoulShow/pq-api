package com.pq.api.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author liutao
 */
public class UserRegisterDto implements Serializable {
    private String phone;

    private String password;

    private int role;

    private int requestFrom;

    private Boolean agree;

    private String name;


    @NotNull(message = "密码必须填写")
    @NotBlank(message = "密码必须填写")
    @Size(min=6, max = 200, message = "登录密码：6-12位数字、字母、符号")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "手机号必须填写")
    @NotBlank(message = "手机号必须填写")
    @Size(max = 11, message = "手机号码格式错误")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(int requestFrom) {
        this.requestFrom = requestFrom;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
