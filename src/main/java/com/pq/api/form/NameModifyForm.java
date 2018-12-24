package com.pq.api.form;

import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liutao
 */
public class NameModifyForm implements Serializable {

    private static final long serialVersionUID = -1673612704736593281L;
    private String name;
    private String userId;

    private int role;

    @NotNull(message = "姓名必须填写")
    @NotBlank(message = "姓名必须填写")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        Client client = ClientContextHolder.getClient();
        return client.getUserRole();
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
