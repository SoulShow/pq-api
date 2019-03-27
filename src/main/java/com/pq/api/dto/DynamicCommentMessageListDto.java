package com.pq.api.dto;

import java.util.List;

public class DynamicCommentMessageListDto {
    private List<DynamicCommentMessageDto> list;

    public List<DynamicCommentMessageDto> getList() {
        return list;
    }

    public void setList(List<DynamicCommentMessageDto> list) {
        this.list = list;
    }
}