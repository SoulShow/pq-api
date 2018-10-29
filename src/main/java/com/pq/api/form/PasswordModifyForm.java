package com.pq.api.form;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 修改密码的表单
 * @author liutao
 */
public class PasswordModifyForm implements Serializable {

    private static final long serialVersionUID = 7790325968489843402L;
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    @NotBlank(message = "请输入原登录密码")
    @NotNull(message = "请输入原登录密码")
    @Size(min = 6, max = 12, message = "密码由6-20位英文字母、数字或符号组成。")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    @NotBlank(message = "请输入新登录密码")
    @NotNull(message = "请输入新登录密码")
    @Size(min = 6, max = 12, message = "密码由6-20位英文字母、数字或符号组成。")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * 新密码必须不同
     *
     * @return
     */
    @AssertTrue(message = "新密码不能跟旧密码相同")
    public boolean isNewPasswordShouldBeDifferent() {
        return !StringUtils.equals(newPassword, oldPassword);
    }


}
