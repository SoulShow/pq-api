package com.pq.api.api;

import com.pq.api.dto.IdDto;
import com.pq.api.dto.UserDynamicDto;
import com.pq.api.dto.UserDynamicListDto;
import com.pq.api.feign.UserFeign;
import com.pq.api.form.*;
import com.pq.api.service.ApiAuthService;
import com.pq.api.service.ApiUserService;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.common.exception.CommonErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

   @Autowired
   private ApiAuthService apiAuthService;
    @Autowired
    private ApiUserService apiUserService;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private QiniuService qiniuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserInfo() {
        return  apiUserService.getUserInfo(getCurrentUserId());
    }

    @RequestMapping(value = "/updatePhone/verificationCode", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult updatePhoneCode(@RequestParam(value = "account") String account,
                                              @RequestParam(value = "type") int type) {
        return apiAuthService.getCaptcha(account, type);
    }

    @RequestMapping(value = "/update/phone", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePhone(@RequestBody UpdatePhoneForm updatePhoneForm, HttpServletRequest request) {
        updatePhoneForm.setSessionId(request.getSession().getId());
        return apiUserService.updatePhone(updatePhoneForm);
    }
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePassword(@RequestBody @Valid PasswordModifyForm passwordModifyForm,HttpServletRequest request) {
        passwordModifyForm.setUserId(getCurrentUserId());
        passwordModifyForm.setSessionId(request.getSession().getId());
        return apiUserService.updatePassword(passwordModifyForm);
    }
    @RequestMapping(value = "update/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult modifyUserAvatar(@RequestParam("avatar")MultipartFile avatar) {
        return apiUserService.modifyUserAvatar(avatar, getCurrentUserId());
    }

    @RequestMapping(value = "update/address", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult modifyUserAddress(@RequestBody UserModifyForm userModifyForm) {
        return apiUserService.modifyUserAddress(userModifyForm.getAddress(), getCurrentUserId());
    }
    @RequestMapping(value = "feedBack", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult feedBack(@RequestBody @Valid FeedbackForm feedbackForm) {
        feedbackForm.setUserId(getCurrentUserId());
        return apiUserService.feedBack(feedbackForm);
    }

    @RequestMapping(value = "dynamic", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserDynamic(@RequestParam("agencyClassId") Long agencyClassId,
                                    @RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "size",required = false)Integer size){
        ApiResult<List<UserDynamicDto>> result = userFeign.getUserDynamic(agencyClassId,getCurrentUserId(),page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        UserDynamicListDto userDynamicListDto = new UserDynamicListDto();
        userDynamicListDto.setDynamicList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(userDynamicListDto);
        return apiResult;
    }

    @RequestMapping(value = "dynamic", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createDynamic(@RequestParam(value = "imgs",required = false)MultipartFile[] imgs,
                                   @RequestParam(value = "movie",required = false)MultipartFile movie,
                                   @RequestParam("agencyClassId") Long agencyClassId,
                                   @RequestParam("name") String name,
                                   @RequestParam(value = "content")String content ){

        String movieUrl = null;
        if(movie!=null && !movie.isEmpty()&& movie.getSize()>0){
            try {
                movieUrl = qiniuService.uploadFile(movie.getBytes(),"user/dynamic");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }

        List<String> imgList = new ArrayList<>();
        for(MultipartFile file :imgs){
            String img = null;
            try {
                img = qiniuService.uploadFile(file.getBytes(),"user/dynamic");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            imgList.add(img);
        }
        UserDynamicForm userDynamicForm = new UserDynamicForm();
        userDynamicForm.setUserId(getCurrentUserId());
        userDynamicForm.setImgList(imgList);
        userDynamicForm.setMovieUrl(movieUrl);
        userDynamicForm.setAgencyClassId(agencyClassId);
        userDynamicForm.setName(name);
        userDynamicForm.setContent(content);
        return userFeign.createDynamic(userDynamicForm);
    }


    @RequestMapping(value = "dynamic/praise", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult praiseDynamic(@RequestBody PraiseDynamicForm praiseDynamicForm){
        praiseDynamicForm.setUserId(getCurrentUserId());
        ApiResult<Long>  result = userFeign.praiseDynamic(praiseDynamicForm);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        IdDto idDto = new IdDto();
        idDto.setId(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(idDto);
        return apiResult;
    }

    @RequestMapping(value = "dynamic/cancel/praise", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult cancelPraiseDynamic(@RequestBody CancelPraiseDynamicForm cancelPraiseDynamicForm){
        return userFeign.cancelPraiseDynamic(cancelPraiseDynamicForm);
    }

    @RequestMapping(value = "dynamic/comment", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createDynamicComment(@RequestBody UserDynamicCommentForm dynamicCommentForm){
        dynamicCommentForm.setOriginatorUserId(getCurrentUserId());
        ApiResult<Long>  result = userFeign.createDynamicComment(dynamicCommentForm);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        IdDto idDto = new IdDto();
        idDto.setId(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(idDto);
        return apiResult;
    }
}