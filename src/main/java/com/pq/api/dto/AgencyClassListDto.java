package com.pq.api.dto;

import java.util.List;

public class AgencyClassListDto {
    private List<AgencyClassInfoDto> list;


    public List<AgencyClassInfoDto> getList() {
        return list;
    }

    public void setList(List<AgencyClassInfoDto> list) {
        this.list = list;
    }
}