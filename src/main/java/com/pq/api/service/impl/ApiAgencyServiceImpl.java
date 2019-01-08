package com.pq.api.service.impl;

import com.pq.api.dto.ClassNoticeDto;
import com.pq.api.dto.NoticeFileDto;
import com.pq.api.dto.StudentLifeDto;
import com.pq.api.dto.TaskCreateDto;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.ClassShowCreateForm;
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
    @Override
    public ApiResult createClassTask(MultipartFile[] imgs,Long agencyClassId,String userId,String title,String content){
        TaskCreateDto taskCreateDto = new TaskCreateDto();
        taskCreateDto.setClassId(agencyClassId);
        taskCreateDto.setTitle(title);
        taskCreateDto.setContent(content);
        taskCreateDto.setUserId(userId);
        List<String> imgList = new ArrayList<>();
        for(MultipartFile file :imgs){
            String img = null;
            try {
                img = qiniuService.uploadFile(file.getBytes(),"task");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            imgList.add(img);
        }
        taskCreateDto.setImgList(imgList);
        return agencyFeign.createClassTask(taskCreateDto);
    }

    @Override
    public ApiResult createClassShow(MultipartFile[] imgs,MultipartFile movie,Long agencyClassId,String userId,String content){
        ClassShowCreateForm taskCreateDto = new ClassShowCreateForm();
        taskCreateDto.setClassId(agencyClassId);
        taskCreateDto.setContent(content);
        taskCreateDto.setUserId(userId);
        List<String> imgList = new ArrayList<>();
        for(MultipartFile file :imgs){
            String img = null;
            try {
                img = qiniuService.uploadFile(file.getBytes(),"show");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            imgList.add(img);
        }
        if(movie !=null&& !movie.isEmpty()&& movie.getSize()>0){
            String movieUrl = null;
            try {
                movieUrl = qiniuService.uploadFile(movie.getBytes(),"show");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            taskCreateDto.setMovieUrl(movieUrl);
        }
        taskCreateDto.setImgList(imgList);
        return agencyFeign.createClassShow(taskCreateDto);
    }

    @Override
    public ApiResult createClassNotice(MultipartFile[] imgs,MultipartFile file,Long agencyClassId,
                                       String userId,String title,String content,int isReceipt,
                                       String fileUrl,String fileName,String fileSize,String fileSuffix){

        List<NoticeFileDto> fileList = new ArrayList<>();
        for(MultipartFile img :imgs){
            NoticeFileDto noticeFileDto = new NoticeFileDto();
            String imgUrl = null;
            try {
                imgUrl = qiniuService.uploadFile(img.getBytes(),"notice");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            noticeFileDto.setFile(imgUrl);
            String filename = img.getOriginalFilename();
            logger.info("图片名称为---------"+filename);
            noticeFileDto.setFileName(filename.substring(0,filename.lastIndexOf(".")));
            noticeFileDto.setFileSize(String.valueOf(img.getSize()));
            noticeFileDto.setSuffix("."+filename.substring(filename.lastIndexOf(".")+1));
            noticeFileDto.setType(1);
            fileList.add(noticeFileDto);
        }
        if(fileUrl==null){
            if(file !=null){
                String url = null;
                try {
                    url = qiniuService.uploadFile(file.getBytes(),"notice");
                } catch (IOException e) {
                    logger.info("上传图片失败"+e);
                    e.printStackTrace();
                }
                NoticeFileDto noticeFileDto = new NoticeFileDto();
                noticeFileDto.setFile(url);
                String filename = file.getOriginalFilename();
                noticeFileDto.setFileName(filename.substring(0,filename.lastIndexOf(".")));
                noticeFileDto.setFileSize(String.valueOf(file.getSize()));
                noticeFileDto.setSuffix("."+filename.substring(filename.lastIndexOf(".")+1));
                noticeFileDto.setType(1);
                fileList.add(noticeFileDto);
            }
        }else {
            NoticeFileDto noticeFileDto = new NoticeFileDto();
            noticeFileDto.setFile(fileUrl);
            noticeFileDto.setFileName(fileName);
            noticeFileDto.setFileSize(fileSize);
            noticeFileDto.setSuffix(fileSuffix);
            noticeFileDto.setType(2);
            fileList.add(noticeFileDto);
        }

        ClassNoticeDto classNoticeDto = new ClassNoticeDto();
        classNoticeDto.setAgencyClassId(agencyClassId);
        classNoticeDto.setContent(content);
        classNoticeDto.setTitle(title);
        classNoticeDto.setUserId(userId);
        classNoticeDto.setIsReceipt(isReceipt);
        classNoticeDto.setFileList(fileList);

        return agencyFeign.createNotice(classNoticeDto);
    }

}
