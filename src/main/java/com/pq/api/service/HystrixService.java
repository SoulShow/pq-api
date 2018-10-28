package com.pq.api.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author liutao
 */
@Service
public class HystrixService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(HystrixService.class);

    @HystrixCommand(fallbackMethod = "myCallBack")
    public String getOrderPageList() {

        //do something
        return "SUCCESS";
    }


}
