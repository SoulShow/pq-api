package com.pq.api.dto;

import java.util.List;

public class TeacherClassCourseDto {
    private List<ClassCourseListDto> list;

    public List<ClassCourseListDto> getList() {
        return list;
    }

    public void setList(List<ClassCourseListDto> list) {
        this.list = list;
    }
}