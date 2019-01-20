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
    ApiResult uploadUserReading (@RequestBody UserReadingRecordDto readingRecordDto);

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
     * 获取阅读评论列表
     * @param readingId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/reading/comment", method = RequestMethod.GET)
    ApiResult<List<StudentReadingCommentDto>> getReadingCommentList(@RequestParam("readingId") Long readingId,
                                                                    @RequestParam(value = "page",required = false) Integer page,
                                                                    @RequestParam(value = "size",required = false) Integer size);

    /**
     * 个人消息列表
     * @param readingId
     * @param studentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/reading/student/message/list", method = RequestMethod.GET)
    ApiResult<List<CommentMessageDto>> getCommentMessageList(@RequestParam("readingId") Long readingId,
                                                             @RequestParam("studentId") Long studentId,
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
     * 评论
     * @param commentDto
     * @return
     */
    @RequestMapping(value = "/reading/student/comment", method = RequestMethod.POST)
    ApiResult comment(@RequestBody ReadingCommentDto commentDto);
}

