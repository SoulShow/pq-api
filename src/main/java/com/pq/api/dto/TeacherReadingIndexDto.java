package com.pq.api.dto;

public class TeacherReadingIndexDto {

   private int readingTaskStatus = 0;

   private int oneToOneStatus = 0;

   public int getOneToOneStatus() {
      return oneToOneStatus;
   }

   public void setOneToOneStatus(int oneToOneStatus) {
      this.oneToOneStatus = oneToOneStatus;
   }

   public int getReadingTaskStatus() {
      return readingTaskStatus;
   }

   public void setReadingTaskStatus(int readingTaskStatus) {
      this.readingTaskStatus = readingTaskStatus;
   }
}