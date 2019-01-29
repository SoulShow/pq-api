package com.pq.api.dto;

import java.util.List;

public class AgencyClassUnReadDto {
    private List<AgencyClassDto> list;

    public List<AgencyClassDto> getList() {
        return list;
    }

    public void setList(List<AgencyClassDto> list) {
        this.list = list;
    }
}