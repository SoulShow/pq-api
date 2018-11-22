package com.pq.api.service;


import com.pq.api.form.StudentModifyForm;
import com.pq.api.form.UserModifyForm;
import com.pq.api.vo.ApiResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liutao
 */
public interface ApiAgencyService {

    /**
     * 修改学生头像
     * @param studentModifyForm
     * @return
     */
    ApiResult modifyStudentAvatar(StudentModifyForm studentModifyForm);

    /**
     * 修改学生性别
     * @param studentModifyForm
     * @return
     */
    ApiResult modifyStudentSex(StudentModifyForm studentModifyForm);
}
