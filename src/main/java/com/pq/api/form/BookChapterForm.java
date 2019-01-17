package com.pq.api.form;

public class BookChapterForm {
   private Long chapterId;

   private String name;

   public Long getChapterId() {
      return chapterId;
   }

   public void setChapterId(Long chapterId) {
      this.chapterId = chapterId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}