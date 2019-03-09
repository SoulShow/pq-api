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
    ApiResult<List<InformationDto>> getInformation(@RequestParam(value = "page",required = false) Integer page,
                                                   @RequestParam(value = "size",required = false) Integer size);

    /**
     * 首页banner
     * @return
     */
    @RequestMapping(value = "/index/banner", method = RequestMethod.GET)
    ApiResult<List<IndexBannerDto>> getIndexBanner();

    /**
     * 首页banner详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/index/bannerdetail", method = RequestMethod.GET)
    ApiResult<IndexBannerDetailDto> getIndexBannerDetail(@RequestParam(value = "id")Long id);


    /**
     * 获取精选专题banner
     * @return
     */
    @RequestMapping(value = "/information/subject/banner", method = RequestMethod.GET)
    ApiResult<SubjectBannerDto> getSubjectBanner();

    /**
     * 获取精选专题
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/information/subject", method = RequestMethod.GET)
    ApiResult<List<SubjectDto>> getSubjectList(@RequestParam(value = "page",required = false) Integer page,
                                               @RequestParam(value = "size",required = false) Integer size);


    /**
     * 版本更新
     * @param client
     * @param versionNo
     * @param platform
     * @return
     */
    @RequestMapping(value = "/information/versionControl", method = RequestMethod.GET)
    ApiResult<ReleaseVersionDto> versionControl(@RequestParam(value = "client") int client,
                                                @RequestParam(value = "versionNo") String versionNo,
                                                @RequestParam(value = "platform") int platform);


    /**
     * 是否含有敏感词
     * @param content
     * @return
     */
    @RequestMapping(value = "/information/isHaveSensitiveWord", method = RequestMethod.GET)
    ApiResult<Boolean> isHaveSensitiveWord(@RequestParam(value = "content") String content);

}
