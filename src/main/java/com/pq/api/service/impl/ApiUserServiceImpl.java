package com.pq.api.service.impl;

import com.pq.api.feign.UserFeign;
import com.pq.api.service.ApiUserService;
import com.pq.api.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liutao
 */
@Service
public class ApiUserServiceImpl implements ApiUserService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserFeign userFeign;

    public static final String USER_AGENT = "XUser-Agent";
    @Override
    public ApiResult getUserInfo(String userId){
        return userFeign.getUserInfo(userId);
    }


}
