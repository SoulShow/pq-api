package com.pq.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author liutao
 */
public class TaskCreateDto implements Serializable {

    private static final long serialVersionUID = -8448529332419646066L;
    private String userId;
    private String content;
    private String title;
    private Long classId;
    private List<String> imgList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
