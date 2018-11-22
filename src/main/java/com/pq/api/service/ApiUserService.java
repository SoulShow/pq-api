package com.pq.api.service;


import com.pq.api.form.PasswordModifyForm;
import com.pq.api.form.UpdatePhoneForm;
import com.pq.api.vo.ApiResult;
import org.springframework.web.multipart.MultipartFile;

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


    /**
     * 修改用户头像
     * @param avatar
     * @param userId
     * @return
     */
    ApiResult modifyUserAvatar(MultipartFile avatar, String userId);

    /**
     * 修改地址
     * @param address
     * @param userId
     * @return
     */
    ApiResult modifyUserAddress(String address, String userId);
}
