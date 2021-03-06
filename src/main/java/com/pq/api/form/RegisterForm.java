package com.pq.api.form;

import com.pq.common.util.OtherUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author liutao
 */
public class RegisterForm implements Serializable {
    private String account;

    private String password;

    private Boolean agree=true;

    private String invitationCode;

    private String studentName;

    private String relation;

    private int role;

    private int requestFrom;


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
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @NotNull(message = "请同意用户协议")
    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    @AssertTrue(message = "手机号码格式错误")
    public boolean isValidMobile() {
        return OtherUtil.verifyPhone(account);
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public int getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(int requestFrom) {
        this.requestFrom = requestFrom;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
