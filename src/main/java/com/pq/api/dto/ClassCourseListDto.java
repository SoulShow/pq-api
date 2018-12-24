package com.pq.api.dto;

import java.util.List;

public class ClassCourseListDto {
    private Long classId;
    private String className;
    private List<ClassCourseDto> classCourseList;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ClassCourseDto> getClassCourseList() {
        return classCourseList;
    }

    public void setClassCourseList(List<ClassCourseDto> classCourseList) {
        this.classCourseList = classCourseList;
    }
}