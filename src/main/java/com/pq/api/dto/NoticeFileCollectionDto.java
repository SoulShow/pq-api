package com.pq.api.dto;

import java.util.List;

public class NoticeFileCollectionDto {
    private List<UserNoticeFileCollectionDto> list;

    public List<UserNoticeFileCollectionDto> getList() {
        return list;
    }

    public void setList(List<UserNoticeFileCollectionDto> list) {
        this.list = list;
    }
}