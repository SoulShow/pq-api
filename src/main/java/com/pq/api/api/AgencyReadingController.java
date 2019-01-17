package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.ReadingFeign;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.common.exception.CommonErrors;
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
@RequestMapping("/reading")
public class AgencyReadingController extends BaseController {
    @Autowired
    private ReadingFeign readingFeign;
    @Autowired
    private QiniuService qiniuService;

    @RequestMapping(value = "/album/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getBookAlbumList(@RequestParam("type") int type,
                                      @RequestParam(value = "page",required = false) Integer page,
                                      @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<BookAlbumDto>> result = readingFeign.getBookAlbumList(type,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        BookAlbumListDto bookAlbumListDto = new BookAlbumListDto();
        bookAlbumListDto.setList(result.getData());
        apiResult.setData(bookAlbumListDto);
        return apiResult;
    }

    @RequestMapping(value = "/book/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getBookAlbumList(@RequestParam("albumId") Long albumId,
                                      @RequestParam(value = "page",required = false) Integer page,
                                      @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<ReadingBookDto>> result = readingFeign.getBookList(albumId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        ReadingBookListDto readingBookListDto = new ReadingBookListDto();
        readingBookListDto.setList(result.getData());
        apiResult.setData(readingBookListDto);
        return apiResult;
    }

    @RequestMapping(value = "/chapter/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getBookChapterList(@RequestParam("bookId") Long bookId,
                                      @RequestParam(value = "page",required = false) Integer page,
                                      @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<BookChapterDto>> result = readingFeign.getBookChapterList(bookId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        BookChapterListDto bookChapterListDto = new BookChapterListDto();
        bookChapterListDto.setList(result.getData());
        apiResult.setData(bookChapterListDto);
        return apiResult;
    }

    @RequestMapping(value = "/teacher/reading/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getTeacherNewReadingList(@RequestParam("classId") Long classId,
                                        @RequestParam(value = "page",required = false) Integer page,
                                        @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<NewReadingDto>> result = readingFeign.getTeacherNewReadingList(classId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        NewReadingListDto newReadingListDto = new NewReadingListDto();
        newReadingListDto.setList(result.getData());
        apiResult.setData(newReadingListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/reading/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getStudentNewReadingList(@RequestParam("classId") Long classId,
                                              @RequestParam("studentId") Long studentId,
                                              @RequestParam(value = "page",required = false) Integer page,
                                              @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<NewReadingDto>> result = readingFeign.getStudentNewReadingList(classId, studentId, getCurrentUserId(), page, size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        NewReadingListDto newReadingListDto = new NewReadingListDto();
        newReadingListDto.setList(result.getData());
        apiResult.setData(newReadingListDto);
        return apiResult;
    }
    @RequestMapping(value = "/task/create", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult teacherCreateTask(@RequestBody CreateReadingTaskDto readingTaskDto){
        readingTaskDto.setUserId(getCurrentUserId());
        return readingFeign.teacherCreateTask(readingTaskDto);
    }

    @RequestMapping(value = "/student/unRead/count", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getStudentNewReadingList(@RequestParam("classId") Long classId,
                                              @RequestParam("studentId") Long studentId){

        ApiResult<Integer> result = readingFeign.getStudentUnReadingCount(classId, studentId, getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        UnReadingCountDto unReadingCountDto = new UnReadingCountDto();
        unReadingCountDto.setUnReadingCount(result.getData());
        apiResult.setData(unReadingCountDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/reading/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getStudentReadingDetail(@RequestParam("taskId") Long taskId,
                                             @RequestParam("studentId") Long studentId){
         return readingFeign.getStudentReadingDetail(taskId, studentId, getCurrentUserId());
    }

    @RequestMapping(value = "/student/album/create", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult studentCreateAlbum(@RequestParam(value = "img",required = false) MultipartFile img,
                                        @RequestParam(value = "name")String name,
                                        @RequestParam(value = "studentId")Long studentId){
        String imgUrl = null;
        if(img!=null && !img.isEmpty()&& img.getSize()>0){
            try {
                imgUrl = qiniuService.uploadFile(img.getBytes(),"reading/album");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }
        UserAlbumDto userAlbumDto = new UserAlbumDto();
        userAlbumDto.setImg(imgUrl);
        userAlbumDto.setName(name);
        userAlbumDto.setStudentId(studentId);
        userAlbumDto.setUserId(getCurrentUserId());
        return readingFeign.studentCreateAlbum(userAlbumDto);
    }

    @RequestMapping(value = "/student/album/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserAlbumList(@RequestParam("studentId") Long studentId){

        ApiResult<List<UserAlbumListDto>> result = readingFeign.getUserAlbumList(studentId, getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        StudentAlbumListDto studentAlbumListDto = new StudentAlbumListDto();
        studentAlbumListDto.setList(result.getData());
        apiResult.setData(studentAlbumListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/upload", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult uploadUserReading(@RequestParam(value = "img",required = false) MultipartFile img,
                                       @RequestParam(value = "voice") MultipartFile voice,
                                       @RequestParam(value = "name")String name,
                                       @RequestParam(value = "taskId",required = false)Long taskId,
                                       @RequestParam(value = "userAlbumId")Long userAlbumId,
                                       @RequestParam(value = "isPrivate")Integer isPrivate,
                                       @RequestParam(value = "oneToOneUserId",required = false)String oneToOneUserId,
                                       @RequestParam(value = "studentId")Long studentId
                                       ){
        UserReadingRecordDto userReadingRecordDto = new UserReadingRecordDto();
        userReadingRecordDto.setTaskId(taskId==null?0:taskId);
        userReadingRecordDto.setName(name);
        userReadingRecordDto.setUserAlbumId(userAlbumId);
        String imgUrl = null;
        if(img!=null && !img.isEmpty()&& img.getSize()>0){
            try {
                imgUrl = qiniuService.uploadFile(img.getBytes(),"reading");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }
        userReadingRecordDto.setIsPrivate(isPrivate);
        userReadingRecordDto.setOneToOneUserId(oneToOneUserId==null?"0":oneToOneUserId);
        userReadingRecordDto.setUserId(getCurrentUserId());
        userReadingRecordDto.setImg(imgUrl);
        String voiceUrl = null;
        try {
            voiceUrl = qiniuService.uploadFile(voice.getBytes(),"reading");
        } catch (IOException e) {
            logger.info("上传音频失败"+e);
            e.printStackTrace();
        }
        userReadingRecordDto.setVoiceUrl(voiceUrl);
        userReadingRecordDto.setStudentId(studentId);
        return readingFeign.uploadUserReading(userReadingRecordDto);
    }

}