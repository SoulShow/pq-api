package com.pq.api.dto;

import java.util.List;

public class VoteOptionListDto {

    private List<VoteOptionDto> list;

    public List<VoteOptionDto> getList() {
        return list;
    }

    public void setList(List<VoteOptionDto> list) {
        this.list = list;
    }
}