package com.pq.api.dto;

import java.util.List;

public class BookAlbumListDto {
    private List<BookAlbumDto> list;


    public List<BookAlbumDto> getList() {
        return list;
    }

    public void setList(List<BookAlbumDto> list) {
        this.list = list;
    }
}