package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.*;
import com.pq.api.vo.ApiResult;
import jdk.nashorn.internal.runtime.arrays.IntElements;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    ApiResult<List<AgencyDto>> getAgencyList(@RequestParam(value = "name",required = false)String name);

    /**
     * 获取年级列表
     *
     * @param agencyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/grade/list", method = RequestMethod.GET)
    ApiResult<List<AgencyDto>> getGradeList(@RequestParam("agencyId")Long agencyId);

    /**
     * 获取班级列表
     *
     * @param agencyId
     * @param gradeId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/class/list", method = RequestMethod.GET)
    ApiResult<List<AgencyDto>> getClassList(@RequestParam("agencyId")Long agencyId,
                                              @RequestParam("gradeId")Long gradeId);

    /**
     * 用户机构注册信息check
     *
     * @param registerCheckDto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/user/student/check", method = RequestMethod.POST)
    ApiResult checkUserInfo(@RequestBody AgencyUserRegisterCheckDto registerCheckDto);

    /**
     * 获取关系
     * @param invitationCode
     * @param studentName
     * @return
     */
    @RequestMapping(value = "/agency/user/student/relation", method = RequestMethod.GET)
    ApiResult<List<String>> getRelation(@RequestParam("invitationCode")String invitationCode,
                                        @RequestParam("studentName")String studentName);

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
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/show", method = RequestMethod.GET)
    ApiResult<AgencyClassShowDto> getClassShow(@RequestParam("agencyClassId")Long agencyClassId,
                                               @RequestParam(value = "userId")String userId,
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
     * @param studentId
     * @param isReceipt
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/notice", method = RequestMethod.GET)
    ApiResult<List<AgencyNoticeDto>> getClassNotice(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                                    @RequestParam(value = "userId")String userId,
                                                    @RequestParam(value = "studentId",required = false)Long studentId,
                                                    @RequestParam(value = "isReceipt")int isReceipt,
                                                    @RequestParam(value = "page",required = false)Integer page,
                                                    @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取班级通知详情
     * @param noticeId
     * @param userId
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/agency/class/notice/detail", method = RequestMethod.GET)
    ApiResult<AgencyNoticeDetailDto> getClassNoticeDetail(@RequestParam(value = "noticeId")Long noticeId,
                                                          @RequestParam(value = "userId")String userId,
                                                          @RequestParam(value = "studentId",required = false)Long studentId);

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
     * @param studentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/user/collection/list", method = RequestMethod.GET)
    ApiResult<List<UserNoticeFileCollectionDto>> collectionList(@RequestParam("userId")String userId,
                                                                @RequestParam(value = "studentId",required = false)Long studentId,
                                                                @RequestParam(value = "page",required = false)Integer page,
                                                                @RequestParam(value = "size",required = false)Integer size);


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
     * 创建班级任务
     * @param taskCreateDto
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/task", method = RequestMethod.POST)
    ApiResult createClassTask(@RequestBody TaskCreateDto taskCreateDto);

    /**
     * 获取班级任务
     * @param agencyClassId
     * @param studentId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/task", method = RequestMethod.GET)
    ApiResult<List<ClassTaskDto>> getClassTask(@RequestParam(value = "agencyClassId")Long agencyClassId,
                                               @RequestParam(value = "userId")String userId,
                                               @RequestParam(value = "studentId")Long studentId,
                                               @RequestParam(value = "page",required = false)Integer page,
                                               @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取老师端班级任务列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/task", method = RequestMethod.GET)
    ApiResult<List<ClassTaskDto>> getTeacherClassTask(@RequestParam(value = "userId")String userId,
                                               @RequestParam(value = "page",required = false)Integer page,
                                               @RequestParam(value = "size",required = false)Integer size);

    /**
     * 获取老师端未读同学信息
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/task/read", method = RequestMethod.GET)
    ApiResult<List<AgencyStudentDto>> getClassTaskRead(@RequestParam(value = "taskId")Long taskId);



    /**
     * 获取班级任务详情
     * @param taskId
     * @param userId
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/agency/class/task/detail", method = RequestMethod.GET)
    ApiResult<ClassTaskDetailDto> getClassTaskDetail(@RequestParam(value = "taskId")Long taskId,
                                                     @RequestParam(value = "studentId",required = false)Long studentId,
                                                     @RequestParam(value = "userId")String userId);


    /**
     * 投票列表
     * @param agencyClassIdList
     * @param userId
     * @param studentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/class/vote/list", method = RequestMethod.GET)
    ApiResult<List<AgencyVoteDto>> getClassVoteList(@RequestParam("agencyClassIdList")List<Long> agencyClassIdList,
                                         @RequestParam(value = "userId")String userId,
                                         @RequestParam(value = "studentId") Long studentId,
                                         @RequestParam(value = "page",required = false)Integer page,
                                         @RequestParam(value = "size",required = false)Integer size);


    /**
     * 投票详情
     * @param voteId
     * @param userId
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/agency/class/vote/detail", method = RequestMethod.GET)
    ApiResult<AgencyVoteDetailDto> getClassVoteDetail(@RequestParam(value = "voteId")Long voteId,
                                           @RequestParam(value = "userId")String userId,
                                           @RequestParam(value = "studentId") Long studentId);

    /**
     * 投票
     * @param voteSelectedForm
     * @return
     */
    @RequestMapping(value = "/agency/class/vote/selected", method = RequestMethod.POST)
    ApiResult classVoteSelect(@RequestBody VoteSelectedForm voteSelectedForm);

    /**
     * 统计
     * @param voteId
     * @return
     */
    @RequestMapping(value = "/agency/class/vote/statistics", method = RequestMethod.GET)
    ApiResult<List<VoteOptionDto>> getClassVoteStatistics(@RequestParam(value = "voteId")Long voteId);


    /**
     * 获取群组信息列表
     *
     * @param studentId
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/group", method = RequestMethod.GET)
    ApiResult<List<AgencyClassInfoDto>> getAgencyGroupList(@RequestParam(value = "studentId",required = false)Long studentId,
                                                           @RequestParam(value = "userId")String userId);

    /**
     * 获取群组用户信息列表
     *
     * @param groupId
     * @param studentId
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/group/user", method = RequestMethod.GET)
    ApiResult<AgencyClassInfoDto> getAgencyGroupUserInfo(@RequestParam(value = "groupId")Long groupId,
                                                         @RequestParam(value = "studentId",required = false)Long studentId,
                                                         @RequestParam(value = "userId")String userId);


    /**
     * 获取老师班级列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/teacher/class/list", method = RequestMethod.GET)
    ApiResult<List<AgencyClassDto>> getTeacherClassList(@RequestParam("userId")String userId);


    /**
     * 开启/关闭消息免打扰
     * @param disturbForm
     * @return
     */
    @RequestMapping(value = "/agency/group/disturb",method = RequestMethod.POST)
    @ResponseBody
    ApiResult groupDisturb(@RequestBody DisturbForm disturbForm);


    /**
     * 获取用户禁言状态
     * @param groupId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/class/user/chatStatus",method = RequestMethod.GET)
    @ResponseBody
    ApiResult<Integer> getGroupChatStatus(@RequestParam(value = "groupId")Long groupId,
                                              @RequestParam(value = "userId")String userId);

    /**
     * 禁言
     * @param chatStatusForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/keepSilent",method = RequestMethod.POST)
    @ResponseBody
    ApiResult groupKeepSilent(@RequestBody ChatStatusForm chatStatusForm);

    /**
     * 获取个人免打扰群组信息列表
     *
     * @param studentId
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/agency/user/disturb/group", method = RequestMethod.GET)
    ApiResult<List<AgencyClassInfoDto>> getDisturbGroup(@RequestParam(value = "studentId",required = false)Long studentId,
                                                        @RequestParam(value = "userId")String userId);


    /**
     * 群组人员搜索
     * @param groupId
     * @param name
     * @return
     */
    @RequestMapping(value = "/agency/group/search/user",method = RequestMethod.GET)
    ApiResult<AgencyClassInfoDto> getGroupSearchUserInfo(@RequestParam(value = "groupId")Long groupId,
                                                         @RequestParam(value = "name",required = false)String name);

    /**
     * 群组转发搜索
     * @param userId
     * @param studentId
     * @param name
     * @param role
     * @return
     */
    @RequestMapping(value = "/agency/group/forward/info",method = RequestMethod.GET)
    ApiResult<ClassAndTeacherInfoDto> getGroupForwardSearchUserInfo(@RequestParam(value = "userId")String userId,
                                               @RequestParam(value = "studentId",required = false)Long studentId,
                                               @RequestParam(value = "name",required = false)String name,
                                               @RequestParam(value = "role")int role);

    /**
     * 创建投票
     * @param voteForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/vote",method = RequestMethod.POST)
    ApiResult createVote(@RequestBody AgencyClassVoteForm voteForm);

    /**
     * 投票删除
     * @param voteDelForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/vote/delete",method = RequestMethod.POST)
    ApiResult deleteVote(@RequestBody VoteDelForm voteDelForm);

    /**
     * 获取班级信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/user/class/info",method = RequestMethod.GET)
    ApiResult<List<AgencyClassDto>> getUserClassInfo(@RequestParam("userId")String userId);

    /**
     * 创建老师
     * @param registerForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/create",method = RequestMethod.POST)
    ApiResult createTeacher(@RequestBody TeacherRegisterForm registerForm);


    /**
     * 班主任check
     * @param checkForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/header/check",method = RequestMethod.POST)
    ApiResult teacherCheck(@RequestBody TeacherCheckForm checkForm);

    /**
     * 更新课程表
     * @param scheduleUpdateForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/schedule",method = RequestMethod.POST)
    ApiResult updateSchedule(@RequestBody ScheduleUpdateForm scheduleUpdateForm);

    /**
     * 获取班级课程
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/course",method = RequestMethod.GET)
    ApiResult<List<ClassCourseListDto>> getTeacherClassCourse(@RequestParam("userId")String userId);

    /**
     * 获取老师职务
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/course",method = RequestMethod.GET)
    ApiResult<UserCourseDto> getTeacherCourse(@RequestParam("userId")String userId);

    /**
     * 添加老师职务
     * @param teacherCourseForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/course",method = RequestMethod.POST)
    ApiResult createTeacherCourse(@RequestBody TeacherCourseForm teacherCourseForm);

    /**
     * 创建班级相册
     * @param classShowCreateForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/show", method = RequestMethod.POST)
    ApiResult createClassShow(@RequestBody ClassShowCreateForm classShowCreateForm);

    /**
     * 删除相册
     * @param showDelForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/show/delete", method = RequestMethod.POST)
    ApiResult deleteShow(@RequestBody ShowDelForm showDelForm);


    /**
     * 创建群组
     * @param groupCreateForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group", method = RequestMethod.POST)
    ApiResult groupCreate(@RequestBody GroupCreateForm groupCreateForm);

    /**
     * 修改群组名称
     * @param groupUpdateForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/name", method = RequestMethod.POST)
    ApiResult updateGroupName(@RequestBody GroupUpdateForm groupUpdateForm);

    /**
     * 修改群组图片
     * @param groupUpdateForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/img", method = RequestMethod.POST)
    ApiResult updateGroupImg(@RequestBody GroupUpdateForm groupUpdateForm);

    /**
     * 添加群组成员
     * @param addGroupMemberForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/add/member", method = RequestMethod.POST)
    ApiResult addGroupMember(@RequestBody AddGroupMemberForm addGroupMemberForm);

    /**
     * 删除群组成员
     * @param delGroupMemberForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/del/member", method = RequestMethod.POST)
    ApiResult delGroupMember(@RequestBody DelGroupMemberForm delGroupMemberForm);

    /**
     * 解散群组
     * @param groupDeleteForm
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/del", method = RequestMethod.POST)
    ApiResult groupDelete(@RequestBody GroupDeleteForm groupDeleteForm);

    /**
     * check群组
     * @param name
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/check", method = RequestMethod.GET)
    ApiResult groupCheck(@RequestParam("name")String name);

    /**
     * 查询用户
     * @param name
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/group/user/search", method = RequestMethod.GET)
    ApiResult<List<ClassUserInfoDto>> searchClassUser(@RequestParam("name")String name,
                                                      @RequestParam("userId")String userId);

    /**
     * 查询班级老师列表
     * @param agencyClassId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/teacher/list", method = RequestMethod.GET)
    ApiResult<List<MemberDto>> getClassTeacherList(@RequestParam("agencyClassId")Long agencyClassId);

    /**
     * 查询班级学生列表
     * @param agencyClassId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/student/list", method = RequestMethod.GET)
    ApiResult<List<MemberDto>> getClassStudentList(@RequestParam("agencyClassId")Long agencyClassId);

    /**
     * 查询班级用户列表
     * @param groupId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/user/list", method = RequestMethod.GET)
    ApiResult<List<ClassUserDto>> getClassUserList(@RequestParam("groupId")Long groupId,
                                                   @RequestParam("userId")String userId);

    /**
     * 查询老师通知列表
     * @param userId
     * @param classId
     * @param isMine
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/notice", method = RequestMethod.GET)
    ApiResult<List<AgencyNoticeDto>> getTeacherNoticeList(@RequestParam("classId")Long classId,
                                                          @RequestParam(value = "userId")String userId,
                                                          @RequestParam(value = "isMine")int isMine,
                                                          @RequestParam(value = "page",required = false)Integer page,
                                                          @RequestParam(value = "size",required = false)Integer size);

    /**
     * 创建通知
     * @param classNoticeDto
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/notice", method = RequestMethod.POST)
    ApiResult createNotice(ClassNoticeDto classNoticeDto);

    /**
     * 获取通知用户状况
     * @param noticeId
     * @param status
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/notice/student", method = RequestMethod.GET)
    ApiResult<List<ReceiptUserDto>> getReceiptStudentList(@RequestParam("noticeId") Long noticeId,@RequestParam("status") int status);

    /**
     * 获取通知用户状况
     * @param noticePushDto
     * @param noticePushDto
     * @return
     */
    @RequestMapping(value = "/agency/teacher/class/notice/push", method = RequestMethod.POST)
    ApiResult  noticePush(@RequestBody NoticePushDto noticePushDto);
}

