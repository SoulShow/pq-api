package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.*;
import com.pq.api.vo.ApiResult;
import org.apache.ibatis.annotations.Param;
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
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/login/try", method = RequestMethod.GET)
    ApiResult<Integer> loginTryTimesRemain(@RequestParam(value = "mobile")String mobile,
                                           @RequestParam(value = "role")int role);

    /**
     * 教师注册
     *
     * @param registerInput
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
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
     * @param role
     * @return
     */
    @RequestMapping(value = "/user/captcha", method = RequestMethod.GET)
    ApiResult<CaptchaDto> getCaptcha(@RequestParam(value = "mobile")String mobile,
                                     @RequestParam(value = "type")String type,
                                     @RequestParam(value = "role")int role);

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
     * 修改老师名称
     * @param nameModifyForm
     * @return
     */
    @RequestMapping(value = "/user/update/name", method = RequestMethod.POST)
    ApiResult updateUserName(@RequestBody NameModifyForm nameModifyForm);

    /**
     * 用户反馈
     * @param feedbackForm
     * @return
     */
    @RequestMapping(value = "/user/feedback", method = RequestMethod.POST)
    ApiResult feedback(@RequestBody FeedbackForm feedbackForm);


    /**
     * 获取用户动态
     * @param agencyClassId
     * @param studentId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/user/dynamic", method = RequestMethod.GET)
    ApiResult<List<UserDynamicDto>> getUserDynamic(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                   @RequestParam(value = "userId")String userId,
                                                   @RequestParam(value = "studentId",required = false) Long studentId,
                                                   @RequestParam(value = "page",required = false)Integer page,
                                                   @RequestParam(value = "size",required = false)Integer size);

    /**
     * 创建动态
     * @param userDynamicForm
     * @return
     */
    @RequestMapping(value = "/user/dynamic", method = RequestMethod.POST)
    ApiResult createDynamic(@RequestBody UserDynamicForm userDynamicForm);


    /**
     * 点赞
     * @param praiseDynamicForm
     * @return
     */
    @RequestMapping(value = "/user/dynamic/praise", method = RequestMethod.POST)
    ApiResult<PraiseDto> praiseDynamic(@RequestBody PraiseDynamicForm praiseDynamicForm);


    /**
     * 取消点赞
     * @param cancelPraiseDynamicForm
     * @return
     */
    @RequestMapping(value = "/user/dynamic/cancel/praise", method = RequestMethod.POST)
    ApiResult<PraiseDto> cancelPraiseDynamic(@RequestBody CancelPraiseDynamicForm cancelPraiseDynamicForm);


    /**
     * 用户评论
     * @param dynamicCommentForm
     * @return
     */
    @RequestMapping(value = "/user/dynamic/comment", method = RequestMethod.POST)
    ApiResult<CommentDto> createDynamicComment(@RequestBody UserDynamicCommentForm dynamicCommentForm);

    /**
     * 删除动态
     * @param dynamicDelForm
     * @return
     */
    @RequestMapping(value = "/user/dynamic/delete", method = RequestMethod.POST)
    ApiResult delDynamic(@RequestBody UserDynamicDelForm dynamicDelForm);


    /**
     * 更新用户极光id
     * @param auroraPushIdForm
     * @return
     */
    @RequestMapping(value = "/user/aurora/pushId", method = RequestMethod.POST)
    ApiResult updateAuroraPushId(@RequestBody AuroraPushIdForm auroraPushIdForm);

    /**
     * 用户踢出
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/admin/logout", method = RequestMethod.GET)
    ApiResult logoutUser(@RequestParam("userId")String userId);


    /**
     * 获取动态详情
     * @param userId
     * @param studentId
     * @param dynamicId
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/user/dynamic/detail", method = RequestMethod.GET)
    ApiResult<UserDynamicDto> getUserDynamicDetail(@RequestParam(value = "userId")String userId,
                                                   @RequestParam(value = "studentId",required = false) Long studentId,
                                                   @RequestParam(value = "dynamicId") Long dynamicId,
                                                   @RequestParam(value = "commentId",required = false) Long commentId);

    /**
     * 获取动态消息
     * @param agencyClassId
     * @param studentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/user/dynamic/message/list", method = RequestMethod.GET)
    ApiResult<List<CommentMessageDto>> getUserDynamicMessageList(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                                 @RequestParam(value = "studentId",required = false) Long studentId,
                                                                 @RequestParam(value = "page",required = false)Integer page,
                                                                 @RequestParam(value = "size",required = false)Integer size);
}
