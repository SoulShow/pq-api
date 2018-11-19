package com.pq.api.feign;


import com.pq.api.dto.UserDto;
import com.pq.api.feign.input.UserRegisterInput;
import com.pq.api.form.AuthForm;
import com.pq.api.form.ForgetPasswordForm;
import com.pq.api.utils.Result;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    ApiResult<String> register(@RequestBody UserRegisterInput registerInput);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    ApiResult<UserDto> getUserInfo(@RequestParam(value = "userId")String userId);
}
