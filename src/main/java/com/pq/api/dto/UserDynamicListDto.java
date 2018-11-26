package com.pq.api.dto;

import java.util.List;

public class UserDynamicListDto {
    private List<UserDynamicDto> dynamicList;

    public List<UserDynamicDto> getDynamicList() {
        return dynamicList;
    }

    public void setDynamicList(List<UserDynamicDto> dynamicList) {
        this.dynamicList = dynamicList;
    }
}