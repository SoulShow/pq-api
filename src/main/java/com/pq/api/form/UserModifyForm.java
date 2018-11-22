package com.pq.api.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author liutao
 */
public class UserModifyForm implements Serializable {

    private static final long serialVersionUID = 4414194841724802043L;
    private MultipartFile avatar;
    private String address;
    private String userId;
    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
