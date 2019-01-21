package com.pq.api.dto;

public class DelUserReadingDto {

   private Long readingId;
   private Long studentId;


   public Long getReadingId() {
      return readingId;
   }

   public void setReadingId(Long readingId) {
      this.readingId = readingId;
   }

   public Long getStudentId() {
      return studentId;
   }

   public void setStudentId(Long studentId) {
      this.studentId = studentId;
   }
}