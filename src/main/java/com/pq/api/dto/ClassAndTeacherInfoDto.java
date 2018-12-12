package com.pq.api.dto;

import java.util.List;

public class ClassAndTeacherInfoDto {
    private List<AgencyForwardClassDto> classList;

    private List<AgencyTeacherDto> teacherList;


    public List<AgencyForwardClassDto> getClassList() {
        return classList;
    }

    public void setClassList(List<AgencyForwardClassDto> classList) {
        this.classList = classList;
    }

    public List<AgencyTeacherDto> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<AgencyTeacherDto> teacherList) {
        this.teacherList = teacherList;
    }
}