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


}

