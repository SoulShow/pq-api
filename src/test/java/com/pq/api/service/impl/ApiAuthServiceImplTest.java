package com.pq.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pq.api.dto.AgencyClassDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class ApiAuthServiceImplTest {

    @Test
    public void login() {

        List<AgencyClassDto> classList = new ArrayList<>();
        AgencyClassDto agencyClassDto = new AgencyClassDto();
        agencyClassDto.setId(1L);
        agencyClassDto.setIsHead(1);

        AgencyClassDto agencyClassDto1 = new AgencyClassDto();
        agencyClassDto1.setId(2L);
        agencyClassDto1.setIsHead(0);
        classList.add(agencyClassDto);
        classList.add(agencyClassDto1);
        String ss = JSON.toJSONString(classList);
        System.out.print("");
    }
}