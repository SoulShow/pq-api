package com.pq.api.dto;

public class AgencyClassDto {
    private Long id;

    private String name;

    private Integer isHead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }
}