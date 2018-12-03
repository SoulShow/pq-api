package com.pq.api.service.impl;

import com.pq.api.dto.StudentLifeDto;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.StudentModifyForm;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.common.exception.CommonErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ApiResult result = agencyFeign.updateStudentAvatar(studentModifyForm);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        Map<String,String> map = new HashMap<>();
        map.put("avatar",avatarUrl);
        result.setData(map);
        return result;
    }

    @Override
    public ApiResult modifyStudentSex(StudentModifyForm studentModifyForm){
        return agencyFeign.updateStudentSex(studentModifyForm);
    }
    @Override
    public  ApiResult createStudentLife(MultipartFile[] imgs,Long agencyClassId,Long studentId,String title,String content){
        StudentLifeDto studentLifeDto = new StudentLifeDto();
        studentLifeDto.setAgencyClassId(agencyClassId);
        studentLifeDto.setStudentId(studentId);
        studentLifeDto.setTitle(title);
        studentLifeDto.setContent(content);
        List<String> imgList = new ArrayList<>();
        for(MultipartFile file :imgs){
            String img = null;
            try {
                img = qiniuService.uploadFile(file.getBytes(),"student/life");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            imgList.add(img);
        }
        studentLifeDto.setImgList(imgList);
        return agencyFeign.createStudentLife(studentLifeDto);
    }


}
