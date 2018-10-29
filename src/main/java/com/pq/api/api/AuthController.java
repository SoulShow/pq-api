package com.pq.api.api;

import com.pq.api.form.AuthForm;
import com.pq.api.utils.Result;
import com.pq.api.web.context.ClientContextHolder;
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

@RestController
public class AuthController {


//    @ResponseBody
//    public Result login(@RequestBody @Valid AuthForm authForm,
//                        HttpServletRequest request,
//                        HttpServletResponse response,
//                        HttpSession session) {
//        if (authForm.getDeviceId() == null) {
//            authForm.setDeviceId(ClientContextHolder.getClient().getDeviceId());
//        }
//        return new Result();
//    }

//
//    @RequestMapping(value = "loginVerification", method = RequestMethod.POST)
//    @ResponseBody
//    public Result login(@RequestBody @Valid PhoneAuthForm authForm,
//                        HttpServletRequest request,
//                        HttpServletResponse response,
//                        HttpSession session) {
//        if (authForm.getDeviceId() == null) {
//            authForm.setDeviceId(ClientContextHolder.getClient().getDeviceId());
//        }
//        return authService.phoneLogin(authForm, request, response, session, OtherUtil.getRequestFrom(getClient().getUserAgent()));
//    }
//
//    @RequestMapping("logout")
//    @ResponseBody
//    public Result logout(HttpSession session) {
//        Result result = new Result();
//        try {
//            loginService.logout(getCurrentUserId(), session.getId());
//        } catch (UserException e) {
//            result.setStatus(e.getErrorCode().getErrorCode());
//            result.setMessage(e.getErrorCode().getErrorMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setStatus(CommonErrors.DB_EXCEPTION.getErrorCode());
//            result.setMessage(CommonErrors.DB_EXCEPTION.getErrorMsg());
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
//    @ResponseBody
//    public Result forgetPassword(@RequestBody @Valid ForgetPasswordForm forgetPasswordForm) {
//        return authService.forgetPassword(forgetPasswordForm);
//    }
//
//    @RequestMapping(value = "/login/verificationCode", method = RequestMethod.GET)
//    @ResponseBody
//    public Result loginVerificationCode(@RequestParam(value = "account") String account,
//                                        @RequestParam(value = "type") int type) {
//        return apiUserService.getCaptcha(account, type);
//    }
//
//    @RequestMapping(value = "/register/verificationCode", method = RequestMethod.GET)
//    @ResponseBody
//    public Result registerVerificationCode(@RequestParam(value = "account") String account,
//                                           @RequestParam(value = "type") int type) {
//        return apiUserService.getCaptcha(account, type);
//    }
//
//    @RequestMapping(value = "/forget/verificationCode", method = RequestMethod.GET)
//    @ResponseBody
//    public Result forgetVerificationCode(@RequestParam(value = "account") String account,
//                                         @RequestParam(value = "type") int type) {
//        return apiUserService.getCaptcha(account, type);
//    }
//
//    @RequestMapping(value = "register", method = RequestMethod.POST)
//    @ResponseBody
//    public Result register(@RequestBody @Valid RegisterForm registerForm, HttpServletRequest request,
//                           HttpServletResponse response,
//                           HttpSession session) {
//        Result result = new Result();
//        registerForm.isValidMobile();
//        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
//        registerRequestDto.setAgree(true);
//        registerRequestDto.setPassword(registerForm.getPassword());
//        registerRequestDto.setPhone(registerForm.getAccount());
//        registerRequestDto.setVerifyCode(registerForm.getVerCode());
//        registerRequestDto.setWxCode(registerForm.getWxCode());
//        //check验证码
//        try {
//            String token = authService.register(registerRequestDto, request, response, session, getClient().getPlatform());
//            Map<String, String> tokenMap = new HashMap<>();
//            tokenMap.put("token", token);
//            result.setData(tokenMap);
//        } catch (UserException e) {
//            result.setStatus(e.getErrorCode().getErrorCode());
//            result.setMessage(e.getErrorCode().getErrorMsg());
//        } catch (Exception e) {
//            result.setStatus(Errors.RegisterFailed.toString());
//            result.setMessage("注册失败,请重试");
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "failed")
//    @ResponseBody
//    public Result failed(HttpServletRequest request) {
//        Result result = new Result();
//        AppErrorCode error = WebUtils.fetchError();
//
//        if (error != null) {
//            result.setStatus(error.getStatus());
//            result.setMessage(error.getMessage());
//        } else {
//            result.setStatus(Errors.TokenInvalid.toString());
//            result.setMessage("您的账号已经在其他设备登录，如非本人操作，请在重新登录后修改登录密码，保障账户安全！");
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
//    @ResponseBody
//    public Result forgetVerificationCode(@RequestParam(value = "account") String account,
//                                         @RequestParam(value = "type") int type,
//                                         @RequestParam(value = "verCode") String verCode) {
//        return apiUserService.checkCode(account, type, verCode);
//    }


}