package com.pq.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liutao
 */
public class MemberListDto implements Serializable {

    private static final long serialVersionUID = -4378589628468978583L;
    private List<MemberDto> list;

    public List<MemberDto> getList() {
        return list;
    }

    public void setList(List<MemberDto> list) {
        this.list = list;
    }
}
