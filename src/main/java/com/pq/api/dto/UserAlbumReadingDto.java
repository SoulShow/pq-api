package com.pq.api.dto;

public class UserAlbumReadingDto {

   private Long id;

   private String name;

   private String bookName;

   private String img;

   private String voiceUrl;

   private String createTime;

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

   public String getVoiceUrl() {
      return voiceUrl;
   }

   public void setVoiceUrl(String voiceUrl) {
      this.voiceUrl = voiceUrl;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBookName() {
      return bookName;
   }

   public void setBookName(String bookName) {
      this.bookName = bookName;
   }

   public String getCreateTime() {
      return createTime;
   }

   public void setCreateTime(String createTime) {
      this.createTime = createTime;
   }
}