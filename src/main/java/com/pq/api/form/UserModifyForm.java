package com.pq.api.form;

import java.io.Serializable;

/**
 * @author liutao
 */
public class UserModifyForm implements Serializable {

    private static final long serialVersionUID = 4414194841724802043L;
    private String avatar;
    private String address;
    private String userId;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
