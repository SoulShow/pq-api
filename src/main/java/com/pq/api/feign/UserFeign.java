package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.*;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 登出
     *
     * @param logoutForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    ApiResult logout(@RequestBody LogoutForm logoutForm);

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

    /**
     * 修改用户手机号
     * @param updatePhoneForm
     * @return
     */
    @RequestMapping(value = "/user/update/phone", method = RequestMethod.POST)
    ApiResult updateUserPhone(@RequestBody UpdatePhoneForm updatePhoneForm);

    /**
     * 修改用户密码
     * @param passwordModifyForm
     * @return
     */
    @RequestMapping(value = "/user/update/password", method = RequestMethod.POST)
    ApiResult updateUserPassword(@RequestBody PasswordModifyForm passwordModifyForm);

    /**
     * 修改用户头像
     * @param userModifyForm
     * @return
     */
    @RequestMapping(value = "/user/update/avatar", method = RequestMethod.POST)
    ApiResult updateUserAvatar(@RequestBody UserModifyForm userModifyForm);

    /**
     * 修改用户地址
     * @param userModifyForm
     * @return
     */
    @RequestMapping(value = "/user/update/address", method = RequestMethod.POST)
    ApiResult updateUserAddress(@RequestBody UserModifyForm userModifyForm);

    /**
     * 用户反馈
     * @param feedbackForm
     * @return
     */
    @RequestMapping(value = "/user/feedback", method = RequestMethod.POST)
    ApiResult feedback(@RequestBody FeedbackForm feedbackForm);


    @RequestMapping(value = "/user/dynamic", method = RequestMethod.GET)
    ApiResult<List<UserDynamicDto>> getUserDynamic(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                   @RequestParam(value = "userId")String userId,
                                                    @RequestParam("page")Integer page,
                                                    @RequestParam("size")Integer size);

    @RequestMapping(value = "/user/dynamic", method = RequestMethod.POST)
    ApiResult createDynamic(@RequestBody UserDynamicForm userDynamicForm);


    @RequestMapping(value = "/user/dynamic/praise", method = RequestMethod.POST)
    ApiResult<PraiseDto> praiseDynamic(@RequestBody PraiseDynamicForm praiseDynamicForm);


    @RequestMapping(value = "/user/dynamic/cancel/praise", method = RequestMethod.POST)
    ApiResult<PraiseDto> cancelPraiseDynamic(@RequestBody CancelPraiseDynamicForm cancelPraiseDynamicForm);


    @RequestMapping(value = "/user/dynamic/comment", method = RequestMethod.POST)
    ApiResult<CommentDto> createDynamicComment(@RequestBody UserDynamicCommentForm dynamicCommentForm);
}
