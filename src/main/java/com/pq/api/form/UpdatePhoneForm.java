package com.pq.api.form;

import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;

import java.io.Serializable;

/**
 * 忘记密码的表单
 *
 * @author liutao
 * @date 15/6/3
 */
public class UpdatePhoneForm implements Serializable {
    private static final long serialVersionUID = 76154478739521808L;
    private String account;
    private String newPhone;
    private String verCode;
    private String sessionId;

    private int role;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getRole() {
        Client client = ClientContextHolder.getClient();
        return client.getUserRole();
    }

    public void setRole(int role) {
        this.role = role;
    }
}

