package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.form.*;
import com.pq.api.vo.ApiResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构服务
 * @author liutao
 */
@FeignClient("service-reading")
public interface ReadingFeign {

    /**
     * 获取专辑列表
     * @param type
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/album/list", method = RequestMethod.GET)
    ApiResult<List<BookAlbumDto>> getBookAlbumList(@RequestParam("type") int type,
                                                   @RequestParam(value = "page",required = false) Integer page,
                                                   @RequestParam(value = "size",required = false) Integer size);
    /**
     * 获取书籍列表
     * @param albumId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/book/list", method = RequestMethod.GET)
    ApiResult<List<ReadingBookDto>> getBookList(@RequestParam("albumId") Long albumId,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "size",required = false) Integer size);

    /**
     * 获取章节列表
     * @param bookId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/chapter/list", method = RequestMethod.GET)
    ApiResult<List<BookChapterDto>> getBookChapterList(@RequestParam("bookId") Long bookId,
                                            @RequestParam(value = "page",required = false) Integer page,
                                            @RequestParam(value = "size",required = false) Integer size);
    /**
     * 获取老师新阅读
     * @param classId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/teacher/reading/list", method = RequestMethod.GET)
    ApiResult<List<NewReadingDto>> getTeacherNewReadingList(@RequestParam("classId") Long classId,
                                                            @RequestParam("userId") String userId,
                                                            @RequestParam(value = "page",required = false) Integer page,
                                                              @RequestParam(value = "size",required = false) Integer size);
    /**
     * 获取学生新阅读
     * @param classId
     * @param studentId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/list", method = RequestMethod.GET)
    ApiResult<List<NewReadingDto> > getStudentNewReadingList(@RequestParam("classId") Long classId,
                                                             @RequestParam("studentId") Long studentId,
                                                             @RequestParam("userId") String userId,
                                                             @RequestParam(value = "page",required = false) Integer page,
                                                             @RequestParam(value = "size",required = false) Integer size);
    /**
     * 创建阅读任务
     * @param readingTaskDto
     * @return
     */
    @RequestMapping(value = "/reading/task/create", method = RequestMethod.POST)
    ApiResult teacherCreateTask(@RequestBody CreateReadingTaskDto readingTaskDto);

    /**
     * 获取未读数量
     * @param classId
     * @param studentId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/student/unRead/count", method = RequestMethod.GET)
    ApiResult<Integer> getStudentUnReadingCount(@RequestParam("classId") Long classId,
                                           @RequestParam("studentId") Long studentId,
                                           @RequestParam("userId") String userId);
    /**
     * 获取阅读详情
     * @param taskId
     * @param studentId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/detail", method = RequestMethod.GET)
    ApiResult<BookChapterDetailDto> getStudentReadingDetail(@RequestParam("taskId") Long taskId,
                                          @RequestParam("studentId") Long studentId,
                                          @RequestParam("userId") String userId);

    /**
     * 创建个人专辑
     * @param userAlbumDto
     * @return
     */
    @RequestMapping(value = "/reading/student/album/create", method = RequestMethod.POST)
    ApiResult studentCreateAlbum(@RequestBody UserAlbumDto userAlbumDto);

    /**
     * 获取个人专辑列表
     * @param studentId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/student/album/list", method = RequestMethod.GET)
    ApiResult<List<UserAlbumListDto>> getUserAlbumList(@RequestParam("studentId") Long studentId,
                                                       @RequestParam("userId") String userId);

    /**
     * 上传个人阅读记录
     * @param readingRecordDto
     * @return
     */
    @RequestMapping(value = "/reading/student/upload", method = RequestMethod.POST)
    ApiResult<Long> uploadUserReading (@RequestBody UserReadingRecordDto readingRecordDto);

    /**
     * 获取个人专辑详情
     * @param albumId
     * @return
     */
    @RequestMapping(value = "/reading/student/album/update", method = RequestMethod.GET)
    ApiResult<UserAlbumListDto> getUserAlbumDetail(@RequestParam("albumId") Long albumId);

    /**
     * 更新个人专辑
     * @param userAlbumDto
     * @return
     */
    @RequestMapping(value = "/reading/student/album/update", method = RequestMethod.POST)
    ApiResult studentUpdateAlbum (@RequestBody UserAlbumDto userAlbumDto);

    /**
     * 删除个人专辑
     * @param userAlbumDto
     * @return
     */
    @RequestMapping(value = "/reading/student/album/delete", method = RequestMethod.POST)
    ApiResult studentDeleteAlbum (@RequestBody UserAlbumDto userAlbumDto);

    /**
     * 获取个人专辑阅读列表
     * @param albumId
     * @param isPrivate
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/album/reading/list", method = RequestMethod.GET)
    ApiResult<List<UserAlbumReadingDto>> getUserAlbumReadingList(@RequestParam("albumId") Long albumId,
                                                                 @RequestParam("isPrivate") int isPrivate,
                                                                 @RequestParam(value = "page",required = false) Integer page,
                                                                 @RequestParam(value = "size",required = false) Integer size);

    /**
     * 我的阅读
     * @param studentId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/student/reading", method = RequestMethod.GET)
    ApiResult<MyReadingDto> getUserReading(@RequestParam("studentId") Long studentId,
                                           @RequestParam("userId") String userId);

    /**
     * 我的阅读详情
     * @param userId
     * @param studentId
     * @param readingId
     * @param commentId
     * @param praiseStudentId
     * @param praiseUserId
     * @param role
     * @return
     */
    @RequestMapping(value = "/reading/student/my/reading/detail", method = RequestMethod.GET)
    ApiResult<MyReadingDetailDto> getUserReadingDetail(@RequestParam("studentId") Long studentId,
                                                       @RequestParam("userId") String userId,
                                                       @RequestParam("readingId") Long readingId,
                                                       @RequestParam(value = "commentId",required = false) Long commentId,
                                                       @RequestParam("praiseUserId") String praiseUserId,
                                                       @RequestParam("praiseStudentId") Long praiseStudentId,
                                                       @RequestParam("role") int role);

    /**
     * 获取阅读评论列表
     * @param readingId
     * @param classId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/comment", method = RequestMethod.GET)
    ApiResult<List<StudentReadingCommentDto>> getReadingCommentList(@RequestParam("readingId") Long readingId,
                                                                    @RequestParam(value = "classId",required = false) Long classId,
                                                                    @RequestParam(value = "page",required = false) Integer page,
                                                                    @RequestParam(value = "size",required = false) Integer size);

    /**
     * 个人消息列表
     * @param studentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/message/list", method = RequestMethod.GET)
    ApiResult<List<CommentMessageDto>> getCommentMessageList(@RequestParam("studentId") Long studentId,
                                                             @RequestParam(value = "page",required = false) Integer page,
                                                             @RequestParam(value = "size",required = false) Integer size);
    /**
     * 点赞
     * @param praiseDto
     * @return
     */
    @RequestMapping(value = "/reading/student/praise", method = RequestMethod.POST)
    ApiResult praise(@RequestBody ReadingPraiseDto praiseDto);

    /**
     * 取消点赞
     * @param praiseDto
     * @return
     */
    @RequestMapping(value = "/reading/student/praise/cancel", method = RequestMethod.POST)
    ApiResult praiseCancel(@RequestBody ReadingPraiseDto praiseDto);

    /**
     * 评论
     * @param commentDto
     * @return
     */
    @RequestMapping(value = "/reading/student/comment", method = RequestMethod.POST)
    ApiResult comment(@RequestBody ReadingCommentDto commentDto);

    /**
     * 获取文章搜索
     * @param name
     * @return
     */
    @RequestMapping(value = "/reading/student/chapter/search", method = RequestMethod.GET)
    ApiResult<ChapterSearchListDto> searchChapter(@RequestParam("name") String name);

    /**
     * 文章或阅读播放
     * @param readingPlayLogDto
     * @return
     */
    @RequestMapping(value = "/reading/chapter/play", method = RequestMethod.POST)
    ApiResult chapterOrRecordPlay(@RequestBody TaskReadingPlayLogDto readingPlayLogDto);

    /**
     * 获取阅读排行榜
     * @param chapterId
     * @param classId
     * @param type
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/ranking/list", method = RequestMethod.GET)
    ApiResult<List<ReadingStudentDto>> getReadingRankingList(@RequestParam("chapterId") Long chapterId,
                                                             @RequestParam(value = "classId",required = false) Long classId,
                                                             @RequestParam("type") int type,
                                                             @RequestParam(value = "page",required = false) Integer page,
                                                             @RequestParam(value = "size",required = false) Integer size);

    /**
     * 删除用户的阅读
     * @param delUserReadingDto
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/delete", method = RequestMethod.POST)
    ApiResult delUserReading(@RequestBody DelUserReadingDto delUserReadingDto);


    /**
     * 一对一列表
     * @param classId
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/teacher/oneToOne/list", method = RequestMethod.GET)
    ApiResult<List<NewReadingDto>> getTeacherOnoToOneList(@RequestParam("classId") Long classId,
                                                          @RequestParam("userId") String userId,
                                                          @RequestParam(value = "page",required = false) Integer page,
                                                          @RequestParam(value = "size",required = false) Integer size);

    /**
     * 提交列表
     * @param classId
     * @param userId
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/reading/teacher/commit/list", method = RequestMethod.GET)
    ApiResult<NewReadingListDto> getTeacherCommitList(@RequestParam("classId") Long classId,
                                                      @RequestParam("taskId") Long taskId,
                                                      @RequestParam("userId") String userId);

    /**
     * 未提交列表
     * @param classId
     * @param userId
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/reading/teacher/unCommit/list", method = RequestMethod.GET)
    ApiResult<ReadingStudentListDto> getTeacherUnCommitList(@RequestParam("classId") Long classId,
                                                           @RequestParam("taskId") Long taskId,
                                                           @RequestParam("userId") String userId);

    /**
     * 一键提醒
     * @param classId
     * @param taskId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/teacher/unCommit/push", method = RequestMethod.GET)
    ApiResult unCommitListPush(@RequestParam("classId") Long classId,
                               @RequestParam("taskId") Long taskId,
                               @RequestParam("userId") String userId);

    /**
     * 获取首页状态
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/teacher/index", method = RequestMethod.GET)
    ApiResult<TeacherReadingIndexDto> getIndexStatus(@RequestParam("userId") String userId);

    /**
     * 获取班级阅读未读数量
     * @param type
     * @param userId
     * @return
     */
    @RequestMapping(value = "/reading/teacher/class/unRead", method = RequestMethod.GET)
    ApiResult<List<AgencyClassDto>> getTeacherClassUnReadCount(@RequestParam("type") int type,
                                                               @RequestParam("userId") String userId);
}

