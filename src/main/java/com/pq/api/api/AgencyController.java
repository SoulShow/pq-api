package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.*;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.ApiUserService;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.common.exception.CommonErrors;
import com.pq.common.util.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("/agency")
public class AgencyController extends BaseController {

    @Autowired
    private ApiAgencyService apiAgencyService;
    @Autowired
    private AgencyFeign agencyFeign;
    @Autowired
    private QiniuService qiniuService;


    @RequestMapping(value = "student/update/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult modifyUserAvatar(@RequestParam("avatar")MultipartFile avatar,@RequestParam("studentId")Long studentId) {

        return apiAgencyService.modifyStudentAvatar(avatar,studentId);
    }

    @RequestMapping(value = "student/update/sex", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult modifyUserAddress(@RequestBody StudentModifyForm studentModifyForm) {
        return apiAgencyService.modifyStudentSex(studentModifyForm);
    }
    @RequestMapping(value = "student/life", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getLifeList(@RequestParam("studentId")Long studentId,
                                 @RequestParam("agencyClassId")Long agencyClassId,
                                 @RequestParam("page")Integer page,
                                 @RequestParam("size")Integer size) {
        return agencyFeign.getStudentLife(studentId,agencyClassId,page,size);
    }
    @RequestMapping(value = "student/life", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createLifeList(@RequestParam(value = "imgs",required = false)MultipartFile[] imgs,
                                 @RequestParam("agencyClassId") Long agencyClassId,
                                 @RequestParam("studentId") Long studentId,
                                 @RequestParam(value = "title",required = false)String title,
                                 @RequestParam(value = "content",required = false)String content) {
        return apiAgencyService.createStudentLife(imgs,agencyClassId,studentId,title,content);
    }

    @RequestMapping(value = "class/show", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getLifeList(@RequestParam("agencyClassId")Long agencyClassId,
                                 @RequestParam(value = "page",required = false)Integer page,
                                 @RequestParam(value = "size",required = false)Integer size) {
        return agencyFeign.getClassShow(agencyClassId,page,size);
    }
    @RequestMapping(value = "show/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAgencyShowList(@RequestParam("agencyClassId")Long agencyClassId,
                                       @RequestParam(value = "page",required = false)Integer page,
                                       @RequestParam(value = "size",required = false)Integer size) {
        ApiResult<List<AgencyShowDto>> result = agencyFeign.getAgencyShow(agencyClassId,0,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyShowListDto showListDto = new AgencyShowListDto();
        showListDto.setList(result.getData());
        apiResult.setData(showListDto);
        return apiResult;
    }
    @RequestMapping(value = "show/banner", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAgencyShowBannerList(@RequestParam("agencyClassId")Long agencyClassId,
                                       @RequestParam(value = "page",required = false)Integer page,
                                       @RequestParam(value = "size",required = false)Integer size) {

        ApiResult<List<AgencyShowDto>> result = agencyFeign.getAgencyShow(agencyClassId,1,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyShowListDto showListDto = new AgencyShowListDto();
        showListDto.setList(result.getData());
        apiResult.setData(showListDto);
        return apiResult;
    }

    @GetMapping(value = "/class/notice")
    @ResponseBody
    public ApiResult getClassNotice(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                       @RequestParam(value = "isReceipt")int isReceipt,
                                       @RequestParam(value = "page",required = false)Integer page,
                                       @RequestParam(value = "size",required = false)Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        int offset = (page - 1) * size;

        ApiResult<List<AgencyNoticeDto>> result = agencyFeign.getClassNotice(agencyClassId,getCurrentUserId(),isReceipt,offset,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyNoticeListDto noticeListDto = new AgencyNoticeListDto();
        noticeListDto.setList(result.getData());
        apiResult.setData(noticeListDto);
        return apiResult;
    }
    @GetMapping(value = "/class/notice/detail")
    @ResponseBody
    public ApiResult getClassNoticeDetail(@RequestParam(value = "noticeId")Long noticeId) {
        return agencyFeign.getClassNoticeDetail(noticeId,getCurrentUserId());
    }

    @PostMapping(value = "/class/notice/receipt")
    @ResponseBody
    public ApiResult getClassNoticeDetail(@RequestParam("img")MultipartFile img,
                                          @RequestParam(value = "noticeId")Long noticeId,
                                          @RequestParam(value = "username")String username) {

        String content = null;
        if(img!=null && !img.isEmpty()&& img.getSize()>0){
            try {
                content = qiniuService.uploadFile(img.getBytes(),"notice/receipt");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }
        NoticeReceiptForm noticeReceiptForm = new NoticeReceiptForm();
        noticeReceiptForm.setUserId(getCurrentUserId());
        noticeReceiptForm.setNoticeId(noticeId);
        noticeReceiptForm.setName(username);
        noticeReceiptForm.setReceiptContent(content);
        return agencyFeign.noticeReceipt(noticeReceiptForm);
    }

    @GetMapping(value = "/user/notice/collection")
    @ResponseBody
    public ApiResult getNoticeCollectionList() {
        ApiResult<List<UserNoticeFileCollectionDto>> result= agencyFeign.collectionList(getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        NoticeFileCollectionDto noticeFileCollectionDto = new NoticeFileCollectionDto();
        noticeFileCollectionDto.setList(result.getData());
        ApiResult apiResult = new ApiResult();
        apiResult.setData(noticeFileCollectionDto);
        return apiResult;
    }
    @PostMapping(value = "/user/notice/collection")
    @ResponseBody
    public ApiResult collectNoticeFile(@RequestBody NoticeFileCollectionForm collectionForm) {
        collectionForm.setUserId(getCurrentUserId());
        return agencyFeign.collectFile(collectionForm);
    }

    @PostMapping(value = "/user/notice/collection/delete")
    @ResponseBody
    public ApiResult deleteCollect(@RequestBody CollectionDeleteForm deleteForm) {
        deleteForm.setUserId(getCurrentUserId());
        return agencyFeign.deleteCollectFile(deleteForm);
    }

    @GetMapping(value = "/class/schedule")
    @ResponseBody
    public ApiResult getClassSchedule(@Param("agencyClassId")Long agencyClassId) {
        ApiResult<List<AgencyClassScheduleDto>> result= agencyFeign.getClassSchedule(agencyClassId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        AgencyClassScheduleListDto scheduleListDto = new AgencyClassScheduleListDto();

        String beginDate = DateUtil.formatDate(DateUtil.getBeginDayOfWeek(),DateUtil.DATE_FORMAT_MONTH_DAY);
        String endDate = DateUtil.formatDate(DateUtil.getFutureDay(DateUtil.getBeginDayOfWeek(),5),DateUtil.DATE_FORMAT_MONTH_DAY);

        scheduleListDto.setList(result.getData());
        scheduleListDto.setDate(beginDate+"-"+endDate);
        ApiResult apiResult = new ApiResult();
        apiResult.setData(scheduleListDto);
        return apiResult;
    }

    @GetMapping(value = "/class/task")
    @ResponseBody
    public ApiResult getClassTask(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                    @RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "size",required = false)Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        int offset = (page - 1) * size;

        ApiResult<List<ClassTaskDto>> result = agencyFeign.getClassTask(agencyClassId,getCurrentUserId(),offset,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        ClassTaskListDto taskListDto = new ClassTaskListDto();
        taskListDto.setList(result.getData());
        apiResult.setData(taskListDto);
        return apiResult;
    }
    @GetMapping(value = "/class/task/detail")
    @ResponseBody
    public ApiResult getClassDetailDetail(@RequestParam(value = "taskId")Long taskId) {
        return agencyFeign.getClassTaskDetail(taskId,getCurrentUserId());
    }

}