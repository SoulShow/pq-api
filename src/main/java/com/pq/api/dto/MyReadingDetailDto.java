package com.pq.api.dto;

public class MyReadingDetailDto {

   private String userName;
   private String className;
   private String avatar;
   private int playCount;
   private int praiseCount;
   private int commentCount;

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getClassName() {
      return className;
   }

   public void setClassName(String className) {
      this.className = className;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }


   public int getPlayCount() {
      return playCount;
   }

   public void setPlayCount(int playCount) {
      this.playCount = playCount;
   }

   public int getPraiseCount() {
      return praiseCount;
   }

   public void setPraiseCount(int praiseCount) {
      this.praiseCount = praiseCount;
   }

   public int getCommentCount() {
      return commentCount;
   }

   public void setCommentCount(int commentCount) {
      this.commentCount = commentCount;
   }

}