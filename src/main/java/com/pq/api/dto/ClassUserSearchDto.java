package com.pq.api.dto;

import java.util.List;

public class ClassUserSearchDto {
    private List<ClassUserInfoDto> list;

    public List<ClassUserInfoDto> getList() {
        return list;
    }

    public void setList(List<ClassUserInfoDto> list) {
        this.list = list;
    }
}