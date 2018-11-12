package com.pq.api.api;

import com.pq.api.exception.AppErrorCode;
import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.utils.WebUtils;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.ClientContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult login(@RequestBody @Valid ForgetPasswordForm forgetPasswordForm) {
        return  apiAuthService.forgetPassword(forgetPasswordForm);
    }

    @RequestMapping(value = "/register/verificationCode", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult registerVerificationCode(@RequestParam(value = "account") String account,
                                           @RequestParam(value = "type") int type) {
        return apiAuthService.getCaptcha(account, type);
    }

    @RequestMapping(value = "/forget/verificationCode", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult forgetVerificationCode(@RequestParam(value = "account") String account,
                                         @RequestParam(value = "type") int type) {
        return apiAuthService.getCaptcha(account, type);
    }
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult forgetVerificationCode(@RequestParam(value = "account") String account,
                                         @RequestParam(value = "type") int type,
                                         @RequestParam(value = "verCode") String verCode) {
        return apiAuthService.checkCode(account, type, verCode);
    }

}