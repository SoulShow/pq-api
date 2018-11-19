package com.pq.api.service;


import com.pq.api.vo.ApiResult;

/**
 * @author liutao
 */
public interface ApiUserService {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ApiResult getUserInfo(String userId);


}
