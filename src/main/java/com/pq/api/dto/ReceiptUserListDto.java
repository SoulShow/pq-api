package com.pq.api.dto;

import java.util.List;

public class ReceiptUserListDto {

   private List<ReceiptUserDto> list;


    public List<ReceiptUserDto> getList() {
        return list;
    }

    public void setList(List<ReceiptUserDto> list) {
        this.list = list;
    }
}