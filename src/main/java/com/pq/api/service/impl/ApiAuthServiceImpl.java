package com.pq.api.service.impl;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.feign.UserFeign;
import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.form.LogoutForm;
import com.pq.api.form.RegisterForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.type.Errors;
import com.pq.api.utils.ConstansAPI;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.api.web.context.SimpleClientResolver;
import com.pq.common.captcha.UserCaptchaType;
import com.pq.common.exception.CommonErrors;
import com.pq.common.util.OtherUtil;
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
    private UserFeign userFeign;
    @Autowired
    private AgencyFeign agencyFeign;

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
            apiResult = userFeign.login(authForm);
            if (apiResult != null && apiResult.getData()!=null) {
                response.addCookie(createCookie(SimpleClientResolver.XToken, session.getId()));
                response.addCookie(createCookie(SimpleClientResolver.XDevice, authForm.getDeviceId()));
            }
            if(!CommonErrors.SUCCESS.getErrorCode().equals(apiResult.getStatus())){
                return apiResult;
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
        if (session.getId() == null) {
            apiResult = checkRemainTimes(authForm.getAccount());
        }
        return apiResult;
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        return cookie;
    }

    private ApiResult checkRemainTimes(String mobile) {
        ApiResult result = new ApiResult();
        result = userFeign.loginTryTimesRemain(mobile);
        Integer remain = (Integer) result.getData();
        if (remain <= 0) {
            result.setStatus(Errors.AccountIsLocked.toString());
            result.setMessage(" 密码错误次数过多，您可以找回密码，或3个小时后再试");
            return result;
        }
        if (remain <= 2) {
            result.setStatus(Errors.CredentialNotMatchTimesRemain.toString());
            result.setMessage("密码错误，您还可以输入" + remain + "次");
            return result;
        }

        result.setStatus(Errors.CredentialNotMatch.toString());
        result.setMessage("用户名密码错误");
        return result;
    }

    @Override
    public ApiResult logout(String userId, String sessionId){
        LogoutForm logoutForm = new LogoutForm();
        logoutForm.setUserId(userId);
        logoutForm.setSessionId(sessionId);
        return userFeign.logout(logoutForm);
    }

    @Override
    public ApiResult forgetPassword(ForgetPasswordForm forgetPasswordForm) {
        ApiResult result = new ApiResult();
        try {
            userFeign.forgetPassword(forgetPasswordForm);
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(CommonErrors.DB_EXCEPTION.getErrorCode());
            result.setMessage(CommonErrors.DB_EXCEPTION.getErrorMsg());
        }
        return result;
    }

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

    @Override
    public ApiResult checkCode(String account, int type, String verCode) {
        ApiResult<Boolean> result = userFeign.captchaVerify(account,type,verCode);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        return result;
    }
    @Override
    public ApiResult getCaptcha(String account, int type) {
        ApiResult<CaptchaDto> result = userFeign.getCaptcha(account,UserCaptchaType.getByIndex(type).getCode());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        Map<String, String> captcha = new HashMap<>();
        captcha.put("verCode", result.getData().getCode());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(captcha);
        return result;
    }

    @Override
    public ApiResult register(RegisterForm registerForm,
                    HttpServletRequest request,
                    HttpServletResponse response,
                    HttpSession session){
        Client client = ClientContextHolder.getClient();
        String userAgent = client.getUserAgent();
        registerForm.setRequestFrom(OtherUtil.getRequestFrom(userAgent));

        AgencyUserRegisterCheckDto registerCheckDto = new AgencyUserRegisterCheckDto();
        registerCheckDto.setInvitationCode(registerForm.getInvitationCode());
        registerCheckDto.setStudentName(registerForm.getStudentName());
        registerCheckDto.setRelation(registerForm.getRelation());
        registerCheckDto.setRole(registerForm.getRole());

        ApiResult userCheckResult = agencyFeign.checkUserInfo(registerCheckDto);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(userCheckResult.getStatus())){
            return userCheckResult;
        }

        UserRegisterDto userRegisterInput = new UserRegisterDto();
        userRegisterInput.setAccount(registerForm.getAccount());
        userRegisterInput.setPassword(registerForm.getPassword());
        userRegisterInput.setRole(registerForm.getRole());
        userRegisterInput.setRequestFrom(registerForm.getRequestFrom());

        ApiResult<String> userResult = userFeign.register(userRegisterInput);

        if(!CommonErrors.SUCCESS.getErrorCode().equals(userResult.getStatus())){
            return userResult;
        }

        AgencyUserRegisterDto registerInput = new AgencyUserRegisterDto();
        registerInput.setRelation(registerForm.getRelation());
        registerInput.setStudentName(registerForm.getStudentName());
        registerInput.setUserId(userResult.getData());
        registerInput.setRole(registerForm.getRole());
        ApiResult apiResult = agencyFeign.createUser(registerInput);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(apiResult.getStatus())){
            return apiResult;
        }

        AuthForm authForm = new AuthForm();
        authForm.setAccount(registerForm.getAccount());
        authForm.setPassword(registerForm.getPassword());
        return login(authForm, request, response, session);
    }

}
