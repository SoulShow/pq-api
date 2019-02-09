package com.pq.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pq.api.dto.AgencyClassDto;
import com.pq.common.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class ApiAuthServiceImplTest {

    @Test
    public void login() {

        Date endDate = DateUtil.getLastDayOfMonth();
        Date beginDate = DateUtil.getBeginDayOfMonth();
        System.out.print("");
        System.out.print("");
    }
}