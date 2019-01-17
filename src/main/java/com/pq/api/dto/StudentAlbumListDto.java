package com.pq.api.dto;

import java.util.List;

public class StudentAlbumListDto {
   private List<UserAlbumListDto> list;

   public List<UserAlbumListDto> getList() {
      return list;
   }

   public void setList(List<UserAlbumListDto> list) {
      this.list = list;
   }
}