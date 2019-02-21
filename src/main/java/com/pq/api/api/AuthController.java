package com.pq.api.api;

import com.pq.api.dto.AgencyUserRegisterCheckDto;
import com.pq.api.dto.RelationDto;
import com.pq.api.dto.UserDto;
import com.pq.api.exception.AppErrorCode;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.feign.UserFeign;
import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.form.RegisterForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.type.Errors;
import com.pq.api.utils.WebUtils;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.common.constants.CommonConstants;
import com.pq.common.exception.CommonErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("")
public class AuthController extends BaseController {

   @Autowired
   private ApiAuthService apiAuthService;
   @Autowired
   private AgencyFeign agencyFeign;
    @Autowired
    private UserFeign userFeign;

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

    @RequestMapping(value = "/parent/register/student/check", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult checkClassAndStudent(@RequestBody @Valid AgencyUserRegisterCheckDto registerCheckDto) {
        registerCheckDto.setRole(CommonConstants.PQ_LOGIN_ROLE_PARENT);
        return agencyFeign.checkUserInfo(registerCheckDto);
    }

    @RequestMapping(value = "/parent/register/relation", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getRelation(@RequestParam(value = "invitationCode") String invitationCode,
                                   @RequestParam(value = "studentName") String studentName) {

        ApiResult<List<String>> result= agencyFeign.getRelation(invitationCode,studentName);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        RelationDto relationDto = new RelationDto();
        relationDto.setList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(relationDto);
        return apiResult;
    }

    @RequestMapping(value = "/parent/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult register(@RequestBody @Valid RegisterForm registerForm, HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session) {
        ApiResult result = new ApiResult();
        registerForm.isValidMobile();
        //check验证码
        try {
            registerForm.setRole(CommonConstants.PQ_LOGIN_ROLE_PARENT);
            result =  apiAuthService.register(registerForm, request, response, session);
        }  catch (Exception e) {
            e.printStackTrace();
            result.setStatus(Errors.RegisterFailed.toString());
            result.setMessage("注册失败,请重试");
        }
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult logout(HttpServletRequest request) {
        return apiAuthService.logout(getCurrentUserId(), request.getSession().getId());
    }

    @RequestMapping(value = "/user/admin/logout",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult logout(@RequestBody UserDto userDto) {
        return userFeign.logoutUser(userDto);
    }


}