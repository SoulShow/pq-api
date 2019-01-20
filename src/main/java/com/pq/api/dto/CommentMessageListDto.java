package com.pq.api.dto;

import java.util.List;

public class CommentMessageListDto {
    private List<CommentMessageDto> list;

    public List<CommentMessageDto> getList() {
        return list;
    }

    public void setList(List<CommentMessageDto> list) {
        this.list = list;
    }
}