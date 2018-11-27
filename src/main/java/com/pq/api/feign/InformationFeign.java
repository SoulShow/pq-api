package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.*;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 资讯
 * @author liutao
 */
@FeignClient("service-information")
public interface InformationFeign {


    /**
     * 资讯列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    ApiResult<List<InformationDto>> getInformation(@RequestParam(value = "page") Integer page,
                                     @RequestParam(value = "size") Integer size);

    /**
     * 首页banner
     * @return
     */
    @RequestMapping(value = "/index/banner", method = RequestMethod.GET)
    ApiResult<List<IndexBannerDto>> getIndexBanner();



}
