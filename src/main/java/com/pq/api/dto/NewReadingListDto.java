package com.pq.api.dto;

import java.util.List;

public class NewReadingListDto {

   private List<NewReadingDto> list;

   public List<NewReadingDto> getList() {
      return list;
   }

   public void setList(List<NewReadingDto> list) {
      this.list = list;
   }
}