package com.pq.api.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class AddStudentForm {

    private Long agencyClassId;

    private Long studentId;

    private String userId;

    private String studentName;

    private String relation;

    @NotNull(message = "学生姓名不能为空")
    @NotBlank(message = "学生姓名不能为空")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @NotNull(message = "班级id不能为空")
    public Long getAgencyClassId() {
        return agencyClassId;
    }

    public void setAgencyClassId(Long agencyClassId) {
        this.agencyClassId = agencyClassId;
    }

    @NotNull(message = "学生id不能为空")
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NotNull(message = "关系不能为空")
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}