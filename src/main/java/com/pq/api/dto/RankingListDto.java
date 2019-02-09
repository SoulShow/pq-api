package com.pq.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author liutao
 */
public class RankingListDto implements Serializable {
    private StudentInfoDto studentInfo;
    private List<RankingDto> list;

    public List<RankingDto> getList() {
        return list;
    }

    public void setList(List<RankingDto> list) {
        this.list = list;
    }

    public StudentInfoDto getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfoDto studentInfo) {
        this.studentInfo = studentInfo;
    }
}