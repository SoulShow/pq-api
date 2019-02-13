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
 */
@Controller
@RequestMapping("")
public class VersionController {

    @Autowired
    private InformationFeign informationFeign;

    @RequestMapping(value = "/versionControl", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult latest(@RequestParam(value = "client") int client,
                            @RequestParam(value = "versionNo") String versionNo) {
        ApiResult result = new ApiResult();
        result.setData(informationFeign.versionControl(client,versionNo));
        return result;
    }


}
