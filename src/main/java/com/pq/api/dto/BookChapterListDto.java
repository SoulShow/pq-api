package com.pq.api.dto;

import java.util.List;

public class BookChapterListDto {
    private List<BookChapterDto> list;

    public List<BookChapterDto> getList() {
        return list;
    }

    public void setList(List<BookChapterDto> list) {
        this.list = list;
    }
}