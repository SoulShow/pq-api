package com.pq.api.dto;

public class UserAlbumListDto {
   private Long id;
   private String name;
   private String img;
   private int count;
   private int type;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImg() {
      return img;
   }

   public void setImg(String img) {
      this.img = img;
   }

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}