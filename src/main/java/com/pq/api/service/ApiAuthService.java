package com.pq.api.service;


import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.form.RegisterForm;
import com.pq.api.form.TeacherRegisterForm;
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

    /**
     * 登出
     * @param userId
     * @param sessionId
     * @return
     */
    ApiResult logout(String userId, String sessionId);


    /**
     * 忘记密码
     *
     * @param forgetPasswordForm
     * @return
     */
    ApiResult forgetPassword(ForgetPasswordForm forgetPasswordForm);

    /**
     * 验证验证码
     *
     * @param account
     * @param type
     * @param verCode
     * @return
     */
    ApiResult checkCode(String account, int type, String verCode);

    /**
     * 获取验证码
     *
     * @param account
     * @param type
     * @return
     */
    ApiResult getCaptcha(String account, int type);

    /**
     * 注册
     *
     * @param registerForm
     * @param request
     * @param response
     * @param session
     * @return
     */
    ApiResult register(RegisterForm registerForm,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    HttpSession session);

    /**
     * 老师注册
     * @param registerForm
     * @param request
     * @param response
     * @param session
     * @return
     */
    ApiResult teacherRegister(TeacherRegisterForm registerForm,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session);

}
