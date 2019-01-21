package com.pq.api.dto;

public class TaskReadingPlayLogDto {

    private Long readingRecordId;

    private Long chapterId;

    private Long studentId;

    private String userId;

    public Long getReadingRecordId() {
        return readingRecordId;
    }

    public void setReadingRecordId(Long readingRecordId) {
        this.readingRecordId = readingRecordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}