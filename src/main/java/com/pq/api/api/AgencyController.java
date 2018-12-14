package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.*;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
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
                                    @RequestParam(value = "studentId")Long studentId,
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

        ApiResult<List<AgencyNoticeDto>> result = agencyFeign.getClassNotice(agencyClassId,getCurrentUserId(),studentId,isReceipt,offset,size);
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
    public ApiResult getClassNoticeDetail(@RequestParam(value = "noticeId")Long noticeId,
                                          @RequestParam(value = "studentId")Long studentId) {
        return agencyFeign.getClassNoticeDetail(noticeId,getCurrentUserId(),studentId);
    }

    @PostMapping(value = "/class/notice/receipt")
    @ResponseBody
    public ApiResult getClassNoticeDetail(@RequestParam("img")MultipartFile img,
                                          @RequestParam(value = "studentId")Long studentId,
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
        noticeReceiptForm.setStudentId(studentId);
        return agencyFeign.noticeReceipt(noticeReceiptForm);
    }

    @GetMapping(value = "/user/notice/collection")
    @ResponseBody
    public ApiResult getNoticeCollectionList(@RequestParam(value = "studentId")Long studentId,
                                             @RequestParam(value = "page",required = false)Integer page,
                                             @RequestParam(value = "size",required = false)Integer size) {
        ApiResult<List<UserNoticeFileCollectionDto>> result= agencyFeign.collectionList(getCurrentUserId(),studentId,page,size);
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
                                  @RequestParam(value = "studentId")Long studentId,
                                    @RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "size",required = false)Integer size) {
        ApiResult<List<ClassTaskDto>> result = agencyFeign.getClassTask(agencyClassId,getCurrentUserId(),studentId,page,size);
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
    public ApiResult getClassDetailDetail(@RequestParam(value = "taskId")Long taskId,
                                          @RequestParam(value = "studentId",required = false)Long studentId) {
        return agencyFeign.getClassTaskDetail(taskId,studentId,getCurrentUserId());
    }

    @GetMapping(value = "/class/vote/list")
    @ResponseBody
    public ApiResult getClassVoteList(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                         @RequestParam(value = "studentId",required = false) Long studentId,
                                         @RequestParam(value = "page",required = false)Integer page,
                                         @RequestParam(value = "size",required = false)Integer size) {

        ApiResult<List<AgencyVoteDto>> result = agencyFeign.getClassVoteList(agencyClassId,getCurrentUserId(),studentId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyVoteListDto agencyVoteListDto = new AgencyVoteListDto();
        agencyVoteListDto.setList(result.getData());
        apiResult.setData(agencyVoteListDto);
        return apiResult;
    }

    @GetMapping(value = "/class/vote/detail")
    @ResponseBody
    public ApiResult getClassVoteDetail(@RequestParam(value = "voteId")Long voteId,
                                        @RequestParam(value = "studentId",required = false) Long studentId) {

        return agencyFeign.getClassVoteDetail(voteId,getCurrentUserId(),studentId);
    }

    @PostMapping(value = "/class/vote/selected")
    @ResponseBody
    public ApiResult classVoteSelect(@RequestBody VoteSelectedForm voteSelectedForm) {
        voteSelectedForm.setUserId(getCurrentUserId());
        return agencyFeign.classVoteSelect(voteSelectedForm);
    }

    @GetMapping(value = "/class/vote/statistics")
    @ResponseBody
    public ApiResult getClassVoteStatistics(@RequestParam(value = "voteId")Long voteId) {
        ApiResult<List<VoteOptionDto>> result = agencyFeign.getClassVoteStatistics(voteId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        VoteOptionListDto voteOptionListDto = new VoteOptionListDto();
        voteOptionListDto.setList(result.getData());
        apiResult.setData(voteOptionListDto);
        return apiResult;
    }

    @GetMapping(value = "/group")
    @ResponseBody
    public ApiResult getGroupList(@RequestParam(value = "studentId",required = false)Long studentId) {
        ApiResult<List<AgencyClassInfoDto>> result = agencyFeign.getAgencyGroupList(studentId,getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyClassListDto agencyClassListDto = new AgencyClassListDto();
        agencyClassListDto.setList(result.getData());
        apiResult.setData(agencyClassListDto);
        return apiResult;
    }
    @GetMapping(value = "/group/user")
    @ResponseBody
    public ApiResult getClassUserList(@RequestParam(value = "groupId",required = false)Long groupId,
                                      @RequestParam(value = "studentId",required = false)Long studentId) {
        return agencyFeign.getAgencyGroupUserInfo(groupId,studentId,getCurrentUserId());
    }

    @PostMapping(value = "/group/disturb")
    @ResponseBody
    public ApiResult groupDisturb(@RequestBody DisturbForm disturbForm) {
        disturbForm.setUserId(getCurrentUserId());
        return agencyFeign.groupDisturb(disturbForm);
    }

    @PostMapping(value = "/user/collection")
    @ResponseBody
    public ApiResult collectFile(@RequestParam("file")MultipartFile file,
                                 @RequestParam(value = "studentId",required = false)Long studentId,
                                 @RequestParam(value = "username")String username) {

        NoticeFileCollectionForm fileCollectionForm = new NoticeFileCollectionForm();
        fileCollectionForm.setUserId(getCurrentUserId());
        fileCollectionForm.setStudentId(studentId);
        fileCollectionForm.setName(username);
        if(file!=null && !file.isEmpty()&& file.getSize()>0){
            try {
                String fileUrl = qiniuService.uploadFile(file.getBytes(),"collection");
                fileCollectionForm.setFileUrl(fileUrl);
                String filename = file.getOriginalFilename();
                fileCollectionForm.setFileName(filename.substring(0,filename.lastIndexOf(".")));
                fileCollectionForm.setFileSize(String.valueOf(file.getSize()));
                fileCollectionForm.setSuffix("."+filename.substring(filename.lastIndexOf(".")+1));
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }
        return agencyFeign.collectFile(fileCollectionForm);
    }

    @GetMapping(value = "/user/chatStatus")
    @ResponseBody
    public ApiResult getGroupChatStatus(@RequestParam(value = "groupId")Long groupId,
                                        @RequestParam(value = "studentId",required = false)Long studentId) {
        ApiResult<Integer> result = agencyFeign.getGroupChatStatus(groupId,studentId,getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }

        ApiResult apiResult = new ApiResult();
        ChatStatusDto chatStatusDto = new ChatStatusDto();
        chatStatusDto.setChatStatus(result.getData());
        apiResult.setData(chatStatusDto);
        return apiResult;
    }


    @GetMapping(value = "/user/disturb/group")
    @ResponseBody
    public ApiResult getDisturbGroup(@RequestParam(value = "studentId",required = false)Long studentId) {
        ApiResult<List<AgencyClassInfoDto>> result = agencyFeign.getDisturbGroup(studentId,getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyClassListDto agencyClassListDto = new AgencyClassListDto();
        agencyClassListDto.setList(result.getData());
        apiResult.setData(agencyClassListDto);
        return apiResult;
    }

    @GetMapping(value = "/group/search/user")
    @ResponseBody
    public ApiResult getGroupSearchUserInfo(@RequestParam(value = "groupId")Long groupId,
                                               @RequestParam(value = "name",required = false)String name) {

        return agencyFeign.getGroupSearchUserInfo(groupId,name);
    }
    @GetMapping(value = "/group/forward/info")
    @ResponseBody
    public ApiResult getGroupForwardSearchUserInfo(@RequestParam(value = "studentId",required = false)Long studentId,
                                                   @RequestParam(value = "name",required = false)String name) {
        Client client = ClientContextHolder.getClient();
        return agencyFeign.getGroupForwardSearchUserInfo(getCurrentUserId(),studentId,name,client.getUserRole());
    }

}