package com.pq.api.dto;

import java.util.List;

public class AgencyNoticeListDto {
    private List<AgencyNoticeDto> list;

    public List<AgencyNoticeDto> getList() {
        return list;
    }

    public void setList(List<AgencyNoticeDto> list) {
        this.list = list;
    }
}