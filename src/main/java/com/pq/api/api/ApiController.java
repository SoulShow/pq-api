package com.pq.api.api;

import com.pq.api.feign.InformationFeign;
import com.pq.api.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return informationFeign.getIndexBanner();
    }

    @RequestMapping(value = "information", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getInformation(@RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "size",required = false)Integer size){
        return informationFeign.getInformation(page,size);
    }


}