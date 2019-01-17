package com.pq.api.dto;

import com.pq.api.form.BookChapterForm;

import java.util.List;

public class CreateReadingTaskDto {
   private List<BookChapterForm> chapterList;

   private List<Long> classIdList;

   private String userId;

   public List<Long> getClassIdList() {
      return classIdList;
   }

   public void setClassIdList(List<Long> classIdList) {
      this.classIdList = classIdList;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public List<BookChapterForm> getChapterList() {
      return chapterList;
   }

   public void setChapterList(List<BookChapterForm> chapterList) {
      this.chapterList = chapterList;
   }
}