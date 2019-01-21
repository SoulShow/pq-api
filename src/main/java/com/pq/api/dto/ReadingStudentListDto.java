package com.pq.api.dto;

import java.util.List;

public class ReadingStudentListDto {
    private List<ReadingStudentDto> list;

    public List<ReadingStudentDto> getList() {
        return list;
    }

    public void setList(List<ReadingStudentDto> list) {
        this.list = list;
    }
}