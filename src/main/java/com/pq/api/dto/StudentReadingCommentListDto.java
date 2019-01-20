package com.pq.api.dto;

import java.util.List;

public class StudentReadingCommentListDto {
    private List<StudentReadingCommentDto> list;


    public List<StudentReadingCommentDto> getList() {
        return list;
    }

    public void setList(List<StudentReadingCommentDto> list) {
        this.list = list;
    }
}