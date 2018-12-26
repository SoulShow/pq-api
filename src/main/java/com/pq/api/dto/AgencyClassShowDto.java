package com.pq.api.dto;

import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;

import java.util.List;

public class AgencyClassShowDto {

    private String className;

    private List<AgencyClassShowDetailDto> showList;

    private String userId;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<AgencyClassShowDetailDto> getShowList() {
        return showList;
    }

    public void setShowList(List<AgencyClassShowDetailDto> showList) {
        this.showList = showList;
    }

    public String getUserId() {
        Client client = ClientContextHolder.getClient();
        if (client.isLogined()) {
            LoginUser loginUser = (LoginUser) client.getLoginUser();
            return loginUser.getUserId();
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}