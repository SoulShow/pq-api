package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.feign.ReadingFeign;
import com.pq.api.service.QiniuService;
import com.pq.api.vo.ApiResult;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.common.exception.CommonErrors;
import com.pq.common.util.StringUtil;
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
    @Autowired
    private AgencyFeign agencyFeign;

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

        ApiResult<List<NewReadingDto>> result = readingFeign.getTeacherNewReadingList(classId,getCurrentUserId(),page,size);
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
                                       @RequestParam(value = "studentId")Long studentId,
                                       @RequestParam(value = "chapterId")Long chapterId,
                                       @RequestParam(value = "duration")String duration){
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
        userReadingRecordDto.setChapterId(chapterId);
        userReadingRecordDto.setDuration(duration);
        ApiResult<Long> result = readingFeign.uploadUserReading(userReadingRecordDto);
        ReadingIdDto readingIdDto = new ReadingIdDto();
        readingIdDto.setReadingId(result.getData());
        ApiResult apiResult= new ApiResult();
        apiResult.setData(readingIdDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/album/update", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserAlbumDetail(@RequestParam("albumId") Long albumId){
       return readingFeign.getUserAlbumDetail(albumId);
    }

    @RequestMapping(value = "/student/album/update", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult studentUpdateAlbum(@RequestParam(value = "img",required = false) MultipartFile img,
                                        @RequestParam(value = "imgUrl",required = false) String imgUrl,
                                       @RequestParam(value = "name")String name,
                                       @RequestParam(value = "albumId")Long albumId){

        UserAlbumDto userAlbumDto = new UserAlbumDto();
        userAlbumDto.setId(albumId);
        userAlbumDto.setName(name);

        if(img!=null && !img.isEmpty()&& img.getSize()>0){
            try {
                imgUrl = qiniuService.uploadFile(img.getBytes(),"userAlbum");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
        }
        userAlbumDto.setImg(imgUrl);
        userAlbumDto.setUserId(getCurrentUserId());
        return readingFeign.studentUpdateAlbum(userAlbumDto);
    }

    @RequestMapping(value = "/student/album/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult studentDeleteAlbum(@RequestBody UserAlbumDto userAlbumDto){
        return readingFeign.studentDeleteAlbum(userAlbumDto);
    }

    @RequestMapping(value = "/student/album/reading/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserAlbumReadingList(@RequestParam("albumId") Long albumId,
                                             @RequestParam("isPrivate") int isPrivate,
                                             @RequestParam(value = "page",required = false) Integer page,
                                             @RequestParam(value = "size",required = false) Integer size){
        ApiResult<List<UserAlbumReadingDto>> result =  readingFeign.getUserAlbumReadingList(albumId,isPrivate,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        UserAlbumReadingListDto readingListDto = new UserAlbumReadingListDto();
        readingListDto.setList(result.getData());
        apiResult.setData(readingListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/reading", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserPrivateReadingList(@RequestParam(value = "studentId")Long studentId){
         return readingFeign.getUserReading(studentId,getCurrentUserId());
    }

    @RequestMapping(value = "/student/my/reading/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserReadingDetail(@RequestParam("studentId") Long studentId,
                                          @RequestParam("readingId") Long readingId,
                                          @RequestParam(value = "praiseStudentId", defaultValue = "0",required = false) Long praiseStudentId,
                                          @RequestParam(value = "commentId",required = false) Long commentId){
        Client client = ClientContextHolder.getClient();
        return readingFeign.getUserReadingDetail(studentId,getCurrentUserId(),readingId,commentId,getCurrentUserId(),praiseStudentId,client.getUserRole());
    }

    @RequestMapping(value = "/student/reading/comment", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getReadingCommentList(@RequestParam("readingId") Long readingId,
                                           @RequestParam(value = "classId",required = false) Long classId,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "size",required = false) Integer size){
        ApiResult<List<StudentReadingCommentDto>> result =  readingFeign.getReadingCommentList(readingId,classId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        StudentReadingCommentListDto commentListDto = new StudentReadingCommentListDto();
        commentListDto.setList(result.getData());
        apiResult.setData(commentListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/message/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getCommentMessageList(@RequestParam("studentId") Long studentId,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "size",required = false) Integer size){
        ApiResult<List<CommentMessageDto>> result =  readingFeign.getCommentMessageList(studentId,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        CommentMessageListDto commentMessageListDto = new CommentMessageListDto();
        commentMessageListDto.setList(result.getData());
        apiResult.setData(commentMessageListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/praise", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult praise(@RequestBody ReadingPraiseDto praiseDto){
        praiseDto.setUserId(getCurrentUserId());
        return readingFeign.praise(praiseDto);
    }

    @RequestMapping(value = "/student/praise/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult cancelPraise(@RequestBody ReadingPraiseDto praiseDto){
        praiseDto.setUserId(getCurrentUserId());
        return readingFeign.praiseCancel(praiseDto);
    }

    @RequestMapping(value = "/student/comment", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult praise(@RequestBody ReadingCommentDto commentDto){
        commentDto.setOriginatorUserId(getCurrentUserId());
        return readingFeign.comment(commentDto);
    }

    @RequestMapping(value = "/student/teachers", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getReadingCommentList(@RequestParam("studentId") Long studentId){
        ApiResult<List<AgencyTeacherDto>> result =  agencyFeign.getTeacherList(studentId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyTeacherListDto teacherListDto = new AgencyTeacherListDto();
        teacherListDto.setList(result.getData());
        apiResult.setData(teacherListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/chapter/search", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult searchChapter(@RequestParam("name") String name){
        return readingFeign.searchChapter(name);
    }

    @RequestMapping(value = "/chapter/play", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult chapterOrRecordPlay(@RequestBody TaskReadingPlayLogDto taskReadingPlayLogDto){
        taskReadingPlayLogDto.setUserId(getCurrentUserId());
        return readingFeign.chapterOrRecordPlay(taskReadingPlayLogDto);
    }

    @RequestMapping(value = "/student/reading/ranking/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getReadingRankingList(@RequestParam("chapterId") Long chapterId,
                                           @RequestParam(value = "classId",required = false) Long classId,
                                           @RequestParam("type") int type,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<ReadingStudentDto>> result =  readingFeign.getReadingRankingList(chapterId,classId,type,page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        ReadingStudentListDto readingStudentListDto = new ReadingStudentListDto();
        readingStudentListDto.setList(result.getData());
        apiResult.setData(readingStudentListDto);
        return apiResult;
    }

    @RequestMapping(value = "/student/reading/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult delUserReading(@RequestBody DelUserReadingDto delUserReadingDto){
        return readingFeign.delUserReading(delUserReadingDto);
    }


    @RequestMapping(value = "/teacher/oneToOne/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getTeacherOnoToOneList(@RequestParam("classId") Long classId,
                                            @RequestParam(value = "page",required = false) Integer page,
                                            @RequestParam(value = "size",required = false) Integer size){

        ApiResult<List<NewReadingDto>> result =  readingFeign.getTeacherOnoToOneList(classId,getCurrentUserId(),page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        NewReadingListDto  readingListDto = new NewReadingListDto();
        readingListDto.setList(result.getData());
        apiResult.setData(readingListDto);
        return apiResult;
    }

    @RequestMapping(value = "/teacher/commit/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getTeacherCommitList(@RequestParam("classId") Long classId,
                                          @RequestParam("taskId") Long taskId){
        return readingFeign.getTeacherCommitList(classId,taskId,getCurrentUserId());
    }

    @RequestMapping(value = "/teacher/unCommit/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getTeacherUnCommitList(@RequestParam("classId") Long classId,
                                          @RequestParam("taskId") Long taskId){
        return readingFeign.getTeacherUnCommitList(classId,taskId,getCurrentUserId());
    }

    @RequestMapping(value = "/teacher/unCommit/push", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult unCommitListPush(@RequestParam("classId") Long classId,
                                      @RequestParam("taskId") Long taskId){
        return readingFeign.unCommitListPush(classId,taskId,getCurrentUserId());
    }

    @RequestMapping(value = "/teacher/index", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getIndexStatus(){
        return readingFeign.getIndexStatus(getCurrentUserId());
    }

    @RequestMapping(value = "/teacher/class/unRead", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getTeacherOnoToOneList(@RequestParam("type") int type){

        ApiResult<List<AgencyClassDto>> result =  readingFeign.getTeacherClassUnReadCount(type,getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyClassUnReadDto  unReadDto = new AgencyClassUnReadDto();
        unReadDto.setList(result.getData());
        apiResult.setData(unReadDto);
        return apiResult;
    }
}