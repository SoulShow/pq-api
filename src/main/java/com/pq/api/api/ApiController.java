package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.InformationFeign;
import com.pq.api.vo.ApiResult;
import com.pq.common.exception.CommonErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("")
public class ApiController extends BaseController {

    @Autowired
    private InformationFeign informationFeign;



    @RequestMapping(value = "/index/banner", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult indexBanner() {
        ApiResult<List<IndexBannerDto>> result = informationFeign.getIndexBanner();
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        IndexBannerListDto indexBannerListDto = new IndexBannerListDto();
        indexBannerListDto.setList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(indexBannerListDto);
        return apiResult;
    }

    @RequestMapping(value = "/index/banner/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult indexBannerDetail(@RequestParam("id")Long id) {
        ApiResult<IndexBannerDetailDto> result = informationFeign.getIndexBannerDetail(id);
        return result;
    }

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getInformation(@RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "size",required = false)Integer size){
        ApiResult<List<InformationDto>> result = informationFeign.getInformation(page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }

        InformationListDto informationListDto = new InformationListDto();
        informationListDto.setList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(informationListDto);
        return apiResult;
    }

    @RequestMapping(value = "/information/subject", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getSubject(@RequestParam(value = "page",required = false)Integer page,
                                      @RequestParam(value = "size",required = false)Integer size){
        ApiResult<List<SubjectDto>> result = informationFeign.getSubjectList(page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }

        SubjectListDto subjectListDto = new SubjectListDto();
        subjectListDto.setList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(subjectListDto);
        return apiResult;
    }

    @RequestMapping(value = "/information/subject/banner", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getSubjectBanner(){
        return  informationFeign.getSubjectBanner();
    }

}