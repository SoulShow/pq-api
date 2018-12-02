package com.pq.api.dto;

import java.util.List;

public class AgencyClassScheduleListDto {
    private String date;

    private List<AgencyClassScheduleDto> list;


    public List<AgencyClassScheduleDto> getList() {
        return list;
    }

    public void setList(List<AgencyClassScheduleDto> list) {
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}