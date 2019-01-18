package com.pq.api.dto;

public class UserAlbumDto {
   private Long id;
   private String name;
   private String img;
   private String userId;
   private Long studentId;

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

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public Long getStudentId() {
      return studentId;
   }

   public void setStudentId(Long studentId) {
      this.studentId = studentId;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}