package com.pq.api.service.impl;

import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.StudentModifyForm;
import com.pq.api.form.UserModifyForm;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liutao
 */
@Service
public class ApiAgencyServiceImpl implements ApiAgencyService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AgencyFeign agencyFeign;
    @Autowired
    private QiniuService qiniuService;
    @Override
    public ApiResult modifyStudentAvatar(MultipartFile avatar, Long studentId){
        String avatarUrl = null;
        try {
            avatarUrl = qiniuService.uploadFile(avatar.getBytes(),"student");
        } catch (IOException e) {
            logger.info("上传头像失败"+e);
            e.printStackTrace();
        }
        StudentModifyForm studentModifyForm = new StudentModifyForm();
        studentModifyForm.setAvatar(avatarUrl);
        studentModifyForm.setStudentId(studentId);
        return agencyFeign.updateStudentAvatar(studentModifyForm);
    }

    @Override
    public ApiResult modifyStudentSex(StudentModifyForm studentModifyForm){
        return agencyFeign.updateStudentSex(studentModifyForm);
    }


}
