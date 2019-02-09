package com.pq.api.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author liutao
 */
public class UserModifyForm implements Serializable {

    private static final long serialVersionUID = 4414194841724802043L;

    private String avatar;

    @NotBlank(message = "地址不能为空")
    @NotNull(message = "地址必须填写")
    @Size(min = 1, max = 36, message = "家庭住址必须为36位以内")
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
