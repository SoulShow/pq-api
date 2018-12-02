package com.pq.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiAuthServiceImplTest {

    @Test
    public void login() {
        Map<String,String> map = new HashMap<>();
        map.put("1","语文");
        map.put("2","数学");
        map.put("3","语文");
        map.put("4","自然");
        map.put("5","体育");
        map.put("6","音乐");
        map.put("7","语文");
        map.put("8","自然");
        String ss = JSONObject.toJSONString(map);

        System.out.print("");
    }
}