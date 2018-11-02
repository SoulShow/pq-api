package com.pq.api.service.impl;

import com.pq.api.dto.UserDto;
import com.pq.api.feign.LoginFeign;
import com.pq.api.form.AuthForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.utils.ConstansAPI;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.SimpleClientResolver;
import com.pq.common.exception.CommonErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liutao
 */
@Service
public class ApiAuthServiceImpl implements ApiAuthService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LoginFeign loginService;

    public static final String USER_AGENT = "XUser-Agent";

    @Override
    public ApiResult login(AuthForm authForm,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        ApiResult apiResult = new ApiResult();
        try {
            HttpSession httpSession = request.getSession();
            authForm.setDeviceId(request.getHeader("XDevice"));
            authForm.setGtClientId(request.getHeader("GTClientId"));
            authForm.setUserAgent(request.getHeader(USER_AGENT));
            authForm.setLoginIp(getRequestIP(request));
            authForm.setSessionId(httpSession.getId());
            apiResult = loginService.login(authForm);
            if (apiResult != null && apiResult.getData()!=null) {
                response.addCookie(createCookie(SimpleClientResolver.XToken, session.getId()));
                response.addCookie(createCookie(SimpleClientResolver.XDevice, authForm.getDeviceId()));
            }
            UserDto userDto = (UserDto) apiResult.getData();

            httpSession.setAttribute(ConstansAPI.SESSION_USER_ID_KEY, userDto.getUserId());

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", session.getId());
            apiResult.setData(tokenMap);
        }catch (Exception e) {
            e.printStackTrace();
            apiResult.setStatus(CommonErrors.DB_EXCEPTION.getErrorCode());
            apiResult.setMessage(CommonErrors.DB_EXCEPTION.getErrorMsg());
            return apiResult;
        }
//        if (session.getId() == null) {
//            result = checkRemainTimes(authForm.getAccount());
//        }
        return apiResult;
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        return cookie;
    }

//
//    @Override
//    public Result phoneLogin(PhoneAuthForm phoneAuthForm,
//                             HttpServletRequest request,
//                             HttpServletResponse response,
//                             HttpSession session, int requestFrom) {
//        Result result = new Result();
//        try {
//            UserDto userDto = loginService.authenticationByCode(phoneAuthForm.getAccount(), phoneAuthForm.getVerCode(), request, requestFrom);
//            if (userDto != null) {
//                response.addCookie(createCookie(SimpleClientResolver.XToken, session.getId()));
//                response.addCookie(createCookie(SimpleClientResolver.XDevice, phoneAuthForm.getDeviceId()));
//            }
//            Map<String, String> tokenMap = new HashMap<>();
//            tokenMap.put("token", session.getId());
//            result.setData(tokenMap);
//        } catch (UserException e) {
//            result.setStatus(e.getErrorCode().getErrorCode());
//            result.setMessage(e.getErrorCode().getErrorMsg());
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setStatus(CommonErrors.DB_EXCEPTION.getErrorCode());
//            result.setMessage(CommonErrors.DB_EXCEPTION.getErrorMsg());
//            return result;
//        }
//        if (session.getId() == null) {
//            result = checkRemainTimes(phoneAuthForm.getAccount());
//        }
//        return result;
//    }
//
//    private ApiResult checkRemainTimes(String mobile) {
//        ApiResult result = new ApiResult();
//        int remain = loginService.loginTryTimesRemain(mobile);
//        if (remain <= 0) {
//            result.setStatus(Errors.AccountIsLocked.toString());
//            result.setMessage(" 密码错误次数过多，您可以找回密码，或3个小时后再试");
//            return result;
//        }
//        if (remain <= 2) {
//            result.setStatus(Errors.CredentialNotMatchTimesRemain.toString());
//            result.setMessage("密码错误，您还可以输入" + remain + "次");
//            return result;
//        }
//
//        result.setStatus(Errors.CredentialNotMatch.toString());
//        result.setMessage("用户名密码错误");
//        return result;
//    }
//
//    @Override
//    public Result forgetPassword(ForgetPasswordForm forgetPasswordForm) {
//        Result result = new Result();
//        try {
//            resetService.resetPassword(forgetPasswordForm.getAccount(), forgetPasswordForm.getNewPassword(), forgetPasswordForm.getRepPassword());
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

    /**
     * 获取请求对象的IP
     *
     * @param request
     * @return
     */
    private String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
