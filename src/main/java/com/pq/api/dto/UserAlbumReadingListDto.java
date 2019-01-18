package com.pq.api.dto;

import java.util.List;

public class UserAlbumReadingListDto {
   private List<UserAlbumReadingDto> list;

   public List<UserAlbumReadingDto> getList() {
      return list;
   }

   public void setList(List<UserAlbumReadingDto> list) {
      this.list = list;
   }
}