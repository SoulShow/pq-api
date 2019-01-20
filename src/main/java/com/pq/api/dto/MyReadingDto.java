package com.pq.api.dto;

public class MyReadingDto {

   private String userName;
   private String className;
   private String avatar;
   private int messageCount;

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

   public int getMessageCount() {
      return messageCount;
   }

   public void setMessageCount(int messageCount) {
      this.messageCount = messageCount;
   }
}