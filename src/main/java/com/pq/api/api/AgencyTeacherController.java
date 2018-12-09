package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.*;
import com.pq.api.service.ApiAgencyService;
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
@RequestMapping("/agency/teacher")
public class AgencyTeacherController extends BaseController {

    @Autowired
    private AgencyFeign agencyFeign;
    @Autowired
    private ApiAgencyService agencyService;

    @GetMapping(value = "/class/task")
    @ResponseBody
    public ApiResult getTeacherClassTask(@RequestParam(value = "page",required = false)Integer page,
                                        @RequestParam(value = "size",required = false)Integer size) {
        ApiResult<List<ClassTaskDto>> result = agencyFeign.getTeacherClassTask(getCurrentUserId(),page,size);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        ClassTaskListDto taskListDto = new ClassTaskListDto();
        taskListDto.setList(result.getData());
        apiResult.setData(taskListDto);
        return apiResult;
    }
    @GetMapping(value = "/class/task/read")
    @ResponseBody
    public ApiResult getClassTaskRead(@RequestParam(value = "taskId")Long taskId) {
        ApiResult<List<AgencyStudentDto>> result = agencyFeign.getClassTaskRead(taskId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyStudentListDto studentListDto = new AgencyStudentListDto();
        studentListDto.setList(result.getData());
        apiResult.setData(studentListDto);
        return apiResult;
    }

    @PostMapping(value = "/class/task")
    @ResponseBody
    public ApiResult createClassTask(@RequestParam(value = "imgs",required = false)MultipartFile[] imgs,
                                     @RequestParam("agencyClassId")Long agencyClassId,
                                     @RequestParam("title")String title,@RequestParam("content")String content) {

        return agencyService.createClassTask(imgs,agencyClassId,getCurrentUserId(),title,content);
    }



}