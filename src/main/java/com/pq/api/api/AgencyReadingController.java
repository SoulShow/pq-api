package com.pq.api.api;

import com.pq.api.dto.AgencyDto;
import com.pq.api.dto.AgencyListDto;
import com.pq.api.feign.AgencyFeign;
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
@RequestMapping("/reading")
public class AgencyReadingController extends BaseController {


    @RequestMapping(value = "/agency/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAgencyList(@RequestParam(value = "name",required = false)String name){
//        ApiResult<List<AgencyDto>> result = agencyFeign.getAgencyList(name);
//        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
//            return result;
//        }
//        ApiResult apiResult = new ApiResult();
//        AgencyListDto agencyListDto = new AgencyListDto();
//        agencyListDto.setList(result.getData());
//        apiResult.setData(agencyListDto);
//        return apiResult;
    }
}