package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.CollectionDeleteForm;
import com.pq.api.form.NoticeFileCollectionForm;
import com.pq.api.form.NoticeReceiptForm;
import com.pq.api.form.StudentModifyForm;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 机构服务
 * @author liutao
 */
@FeignClient("service-agency")
public interface AgencyFeign {

    /**
     * 获取机构列表
     *
     * @param name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/list", method = RequestMethod.GET)
    ApiResult<List<Agency>> getAgencyList(@RequestParam("name")String name);

    /**
     * 获取年级列表
     *
     * @param agencyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/grade/list", method = RequestMethod.GET)
    ApiResult<List<Grade>> getGradeList(@RequestParam("agencyId")Long agencyId);

    /**
     * 获取班级列表
     *
     * @param agencyId
     * @param gradeId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/class/list", method = RequestMethod.GET)
    ApiResult<List<AgencyClass>> getGradeList(@RequestParam("agencyId")Long agencyId,
                                              @RequestParam("gradeId")Long gradeId);

    /**
     * 用户机构注册信息check
     *
     * @param registerCheckDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/user/check", method = RequestMethod.POST)
    ApiResult checkUserInfo(@RequestBody AgencyUserRegisterCheckDto registerCheckDto);


    /**
     * 获取学生信息
     * @param agencyId
     * @param gradeId
     * @param classId
     * @param studentName
     * @return
     */
    @RequestMapping(value = "/agency/user/student/check", method = RequestMethod.GET)
    ApiResult checkAgencyUserStudent(@RequestParam("agencyId")Long agencyId,@RequestParam("gradeId")Long gradeId,
                                           @RequestParam("classId")Long classId,@RequestParam("studentName")String studentName);

    /**
     * 用户机构注册
     *
     * @param userRegisterInput
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/user/create", method = RequestMethod.POST)
    ApiResult createUser(@RequestBody AgencyUserRegisterDto userRegisterInput);


    /**
     * 修改学生头像
     *
     * @param studentModifyForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/student/update/avatar", method = RequestMethod.POST)
    ApiResult updateStudentAvatar(@RequestBody StudentModifyForm studentModifyForm);

    /**
     * 修改学生性别
     *
     * @param studentModifyForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/student/update/sex", method = RequestMethod.POST)
    ApiResult updateStudentSex(@RequestBody StudentModifyForm studentModifyForm);


    /**
     * 获取学生成长动态
     * @param studentId
     * @param agencyClassId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/student/life", method = RequestMethod.GET)
    ApiResult<AgencyStudentLifeListDto> getStudentLife(@RequestParam("studentId")Long studentId,
                                     @RequestParam("agencyClassId")Long agencyClassId,
                                     @RequestParam(value = "page",required = false)Integer page,
                                     @RequestParam(value = "size",required = false)Integer size);
    /**
     * 创建学生成长动态
     *
     * @param studentLifeDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/student/life", method = RequestMethod.POST)
    ApiResult createStudentLife(@RequestBody StudentLifeDto studentLifeDto);


    /**
     * 获取班级风采
     * @param agencyClassId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/show", method = RequestMethod.GET)
    ApiResult<AgencyClassShowDto> getClassShow(@RequestParam("agencyClassId")Long agencyClassId,
                                                       @RequestParam(value = "page",required = false)Integer page,
                                                       @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取校园风采
     * @param agencyClassId
     * @param isBanner
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/show", method = RequestMethod.GET)
    ApiResult<List<AgencyShowDto>> getAgencyShow(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                 @RequestParam(value = "isBanner")int isBanner,
                                                 @RequestParam(value = "page",required = false)Integer page,
                                                 @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取班级通知
     * @param agencyClassId
     * @param userId
     * @param isReceipt
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/notice", method = RequestMethod.GET)
    ApiResult<List<AgencyNoticeDto>> getClassNotice(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                    @RequestParam(value = "userId")String userId,
                                                    @RequestParam(value = "isReceipt")int isReceipt,
                                                    @RequestParam(value = "page",required = false)Integer page,
                                                    @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取班级通知详情
     * @param noticeId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/class/notice/detail", method = RequestMethod.GET)
    ApiResult<AgencyNoticeDetailDto> getClassNoticeDetail(@RequestParam(value = "noticeId")Long noticeId,
                                                          @RequestParam(value = "userId")String userId);

    /**
     * 通知回执
     * @param noticeReceiptForm
     * @return
     */
    @RequestMapping(value = "/agency/class/notice/receipt", method = RequestMethod.POST)
    ApiResult noticeReceipt(@RequestBody NoticeReceiptForm noticeReceiptForm);

    /**
     * 用户通知文件收藏
     * @param collectionForm
     * @return
     */
    @RequestMapping(value = "/agency/user/collection", method = RequestMethod.POST)
    ApiResult collectFile(@RequestBody NoticeFileCollectionForm collectionForm);

    /**
     * 用户通知文件收藏列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/user/collection/list", method = RequestMethod.GET)
    ApiResult<List<UserNoticeFileCollectionDto>> collectionList(@RequestParam("userId")String userId);


    /**
     * 用户通知文件收藏删除
     * @param deleteForm
     * @return
     */
    @RequestMapping(value = "/agency/user/collection/delete", method = RequestMethod.POST)
    ApiResult deleteCollectFile(@RequestBody CollectionDeleteForm deleteForm);

    /**
     * 获取班级课表
     * @param agencyClassId
     * @return
     */
    @RequestMapping(value = "/agency/class/schedule", method = RequestMethod.GET)
    ApiResult<List<AgencyClassScheduleDto>> getClassSchedule(@RequestParam("agencyClassId")Long agencyClassId);

    /**
     * 获取班级任务
     * @param agencyClassId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/task", method = RequestMethod.GET)
    ApiResult<List<ClassTaskDto>> getClassTask(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                               @RequestParam(value = "userId")String userId,
                                               @RequestParam(value = "page",required = false)Integer page,
                                               @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取班级任务详情
     * @param taskId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/class/task/detail", method = RequestMethod.GET)
    ApiResult<ClassTaskDetailDto> getClassTaskDetail(@RequestParam(value = "taskId")Long taskId,
                                                     @RequestParam(value = "userId")String userId);

}
