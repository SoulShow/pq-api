package com.pq.api.service;


import com.pq.api.form.AuthForm;
import com.pq.api.type.OSPlatform;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author liutao
 */
public interface ApiAuthService {

    /**
     * 登陆
     *
     * @param authForm
     * @param request
     * @param response
     * @param session
     * @return
     */
    ApiResult login(AuthForm authForm,
                 HttpServletRequest request,
                 HttpServletResponse response,
                 HttpSession session);

//    /**
//     * 手机登陆
//     *
//     * @param phoneAuthForm
//     * @param request
//     * @param response
//     * @param session
//     * @param requestFrom
//     * @return
//     */
//    ApiResult phoneLogin(PhoneAuthForm phoneAuthForm,
//                      HttpServletRequest request,
//                      HttpServletResponse response,
//                      HttpSession session, int requestFrom);
//
//    /**
//     * 忘记密码
//     *
//     * @param forgetPasswordForm
//     * @return
//     */
//    ApiResult forgetPassword(ForgetPasswordForm forgetPasswordForm);
}
