package com.pq.api.dto;

import com.pq.common.util.OtherUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AgencyUserRegisterCheckDto {

    @NotBlank(message = "手机号码不能为空")
    @NotNull(message = "手机号码必须填写")
    @Size(min = 11, max = 11, message = "请输入11位手机号码")
    private String phone;

    @NotBlank(message = "学生姓名不能为空")
    @NotNull(message = "学生姓名必须填写")
    private String studentName;

    @NotBlank(message = "邀请码不能为空")
    @NotNull(message = "邀请码必须填写")
    private String invitationCode;

    private int role;

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @AssertTrue(message = "手机号码不符合格式")
    public boolean isValidMobile() {
        return OtherUtil.verifyPhone(phone);
    }
}