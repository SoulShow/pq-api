package com.pq.api.service;


import com.pq.api.form.StudentModifyForm;
import com.pq.api.form.UserModifyForm;
import com.pq.api.vo.ApiResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liutao
 */
public interface ApiAgencyService {

    /**
     * 修改学生头像
     * @param avatar
     * @param studentId
     * @return
     */
    ApiResult modifyStudentAvatar(MultipartFile avatar, Long studentId);

    /**
     * 修改学生性别
     * @param studentModifyForm
     * @return
     */
    ApiResult modifyStudentSex(StudentModifyForm studentModifyForm);

    /**
     * 创建学生成长动态
     * @param imgs
     * @param agencyClassId
     * @param studentId
     * @param title
     * @param content
     * @return
     */
    ApiResult createStudentLife(MultipartFile[] imgs,Long agencyClassId,Long studentId,String title,String content);
}
