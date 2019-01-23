package com.pq.api.dto;

import java.util.List;

public class ReadingStudentListDto {

    private int count;

    private List<ReadingStudentDto> list;

    public List<ReadingStudentDto> getList() {
        return list;
    }

    public void setList(List<ReadingStudentDto> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}