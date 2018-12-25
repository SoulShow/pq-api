package com.pq.api.form;

import java.io.Serializable;

/**
 * @author liutao
 */
public class ShowDelForm implements Serializable {

    private static final long serialVersionUID = 946996555237041184L;
    private Long showId;
    private String userId;

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
