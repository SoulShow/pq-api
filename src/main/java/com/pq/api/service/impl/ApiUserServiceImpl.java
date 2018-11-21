package com.pq.api.service.impl;

import com.pq.api.feign.UserFeign;
import com.pq.api.form.PasswordModifyForm;
import com.pq.api.form.UpdatePhoneForm;
import com.pq.api.service.ApiUserService;
import com.pq.api.vo.ApiResult;
import com.pq.common.captcha.UserCaptchaType;
import com.pq.common.exception.CommonErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liutao
 */
@Service
public class ApiUserServiceImpl implements ApiUserService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserFeign userFeign;

    @Override
    public ApiResult getUserInfo(String userId){
        return userFeign.getUserInfo(userId);
    }

    @Override
    public  ApiResult updatePhone(UpdatePhoneForm updatePhoneForm){
        ApiResult result = userFeign.captchaVerify(updatePhoneForm.getNewPhone(),UserCaptchaType.UPDATE_PHONE.getIndex(),updatePhoneForm.getVerCode());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        return userFeign.updateUserPhone(updatePhoneForm);
    }
    @Override
    public ApiResult updatePassword(PasswordModifyForm passwordModifyForm){
        ApiResult apiResult = new ApiResult();
        try{
            passwordModifyForm.isNewPasswordShouldBeDifferent();
            passwordModifyForm.isSamePassword();
            apiResult = userFeign.updateUserPassword(passwordModifyForm);
        }catch (Exception e){
            apiResult.setStatus(CommonErrors.DB_EXCEPTION.getErrorCode());
            apiResult.setMessage(e.getMessage());
        }
        return apiResult;
    }


}
