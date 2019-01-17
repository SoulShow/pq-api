package com.pq.api.dto;

import java.util.List;

public class ReadingBookListDto {
    private List<ReadingBookDto> list;

    public List<ReadingBookDto> getList() {
        return list;
    }

    public void setList(List<ReadingBookDto> list) {
        this.list = list;
    }
}