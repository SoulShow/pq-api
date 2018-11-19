package com.pq.api.feign;


import com.pq.api.dto.CaptchaDto;
import com.pq.api.dto.UserDto;
import com.pq.api.dto.UserRegisterDto;
import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录服务
 * @author liutao
 */
@FeignClient("service-user")
public interface UserFeign {

    /**
     * 登录
     *
     * @param authForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    ApiResult<UserDto> login(@RequestBody AuthForm authForm);

    /**
     * 修改密码
     *
     * @param forgetPasswordForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/forgetPassword", method = RequestMethod.POST)
    ApiResult forgetPassword(@RequestBody ForgetPasswordForm forgetPasswordForm);

    /**
     * 登录错误次数
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/login/try", method = RequestMethod.GET)
    ApiResult<Integer> loginTryTimesRemain(@RequestParam(value = "mobile")String mobile);

    /**
     * 教师注册
     *
     * @param registerInput
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    ApiResult<String> register(@RequestBody UserRegisterDto registerInput);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    ApiResult<UserDto> getUserInfo(@RequestParam(value = "userId")String userId);

    /**
     * 获取验证码
     * @param mobile
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/captcha", method = RequestMethod.GET)
    ApiResult<CaptchaDto> getCaptcha(@RequestParam(value = "mobile")String mobile,
                                     @RequestParam(value = "type")String type);

    /**
     * 验证验证码
     * @param mobile
     * @param type
     * @param code
     * @return
     */
    @RequestMapping(value = "/user/captcha/verify", method = RequestMethod.GET)
    ApiResult captchaVerify(@RequestParam(value = "mobile")String mobile,
                                        @RequestParam(value = "type")int type,
                                        @RequestParam(value = "code")String code);
}
