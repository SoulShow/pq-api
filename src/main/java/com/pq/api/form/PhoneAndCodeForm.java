package com.pq.api.form;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PhoneAndCodeForm implements Serializable {
    private String mobile;
    private String code;

    @NotBlank(message = "手机号码不能为空")
    @NotNull(message = "手机号码必须填写")
    @Size(min = 11, max = 11, message = "请输入11位手机号码")
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = StringUtils.trim(mobile);
    }


    @NotBlank(message="验证码必须填写")
    @NotNull(message="验证码必须填写")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
