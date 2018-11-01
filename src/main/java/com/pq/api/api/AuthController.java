package com.pq.api.api;

import com.pq.api.exception.AppErrorCode;
import com.pq.api.form.AuthForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.utils.WebUtils;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.ClientContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("")
public class AuthController extends BaseController {

   @Autowired
   private ApiAuthService apiAuthService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult login(@RequestBody @Valid AuthForm authForm,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        if (authForm.getDeviceId() == null) {
            authForm.setDeviceId(ClientContextHolder.getClient().getDeviceId());
        }
        return  apiAuthService.login(authForm,request,response,session);
    }


    @RequestMapping(value = "failed")
    @ResponseBody
    public ApiResult failed() {
        ApiResult result = new ApiResult();
        AppErrorCode error = WebUtils.fetchError();

        if (error != null) {
            result.setStatus(error.getStatus());
            result.setMessage(error.getMessage());
        }
        return result;
    }


}