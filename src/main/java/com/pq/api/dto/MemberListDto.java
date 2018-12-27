package com.pq.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liutao
 */
public class MemberListDto implements Serializable {

    private static final long serialVersionUID = -4378589628468978583L;
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
