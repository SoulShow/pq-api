package com.pq.api.api;

import com.pq.api.service.ApiUserService;
import com.pq.api.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

   @Autowired
   private ApiUserService apiUserService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserInfo() {
        return  apiUserService.getUserInfo(getCurrentUserId());
    }


}