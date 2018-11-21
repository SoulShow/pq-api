package com.pq.api.service;


import com.pq.api.form.PasswordModifyForm;
import com.pq.api.form.UpdatePhoneForm;
import com.pq.api.vo.ApiResult;

/**
 * @author liutao
 */
public interface ApiUserService {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ApiResult getUserInfo(String userId);

    /**
     * 更换手机号
     * @param updatePhoneForm
     * @return
     */
    ApiResult updatePhone(UpdatePhoneForm updatePhoneForm);


    /**
     * 更换密码
     * @param passwordModifyForm
     * @return
     */
    ApiResult updatePassword(PasswordModifyForm passwordModifyForm);
}
