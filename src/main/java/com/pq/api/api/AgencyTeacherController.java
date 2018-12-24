package com.pq.api.api;

import com.pq.api.dto.*;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.feign.UserFeign;
import com.pq.api.form.*;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.ApiAuthService;
import com.pq.api.service.QiniuService;
import com.pq.api.type.Errors;
import com.pq.api.vo.ApiResult;
import com.pq.common.constants.CommonConstants;
import com.pq.common.exception.CommonErrors;
import com.pq.common.util.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private ApiAuthService apiAuthService;
    @Autowired
    private UserFeign userFeign;

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
    @GetMapping(value = "/class")
    @ResponseBody
    public ApiResult getTeacherClass() {
        ApiResult<List<AgencyClassDto>> result = agencyFeign.getTeacherClassList(getCurrentUserId());
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        TeacherClassListDto teacherClassListDto = new TeacherClassListDto();
        teacherClassListDto.setList(result.getData());
        apiResult.setData(teacherClassListDto);
        return apiResult;
    }

    @PostMapping(value = "/class/vote")
    @ResponseBody
    public ApiResult createVote(@RequestParam(value = "imgs",required = false)MultipartFile[] imgs,
                                @RequestParam("agencyClassId")Long agencyClassId,
                                @RequestParam("title")String title,@RequestParam("deadline")String deadline,
                                @RequestParam("type")int type,@RequestParam("isSecret")int isSecret,
                                @RequestParam("optionList")List<String> optionList) {

        AgencyClassVoteForm classVoteForm = new AgencyClassVoteForm();
        classVoteForm.setUserId(getCurrentUserId());
        classVoteForm.setAgencyClassId(agencyClassId);
        classVoteForm.setDeadline(deadline);
        classVoteForm.setTitle(title);classVoteForm.setType(type);
        classVoteForm.setIsSecret(isSecret);
        classVoteForm.setOptionList(optionList);
        List<String> imgList = new ArrayList<>();
        for(MultipartFile file :imgs){
            String img = null;
            try {
                img = qiniuService.uploadFile(file.getBytes(),"vote");
            } catch (IOException e) {
                logger.info("上传图片失败"+e);
                e.printStackTrace();
            }
            imgList.add(img);
        }
        classVoteForm.setImgList(imgList);
        return agencyFeign.createVote(classVoteForm);
    }

    @PostMapping(value = "/class/vote/delete")
    @ResponseBody
    public ApiResult deleteVote(@RequestBody VoteDelForm voteDelForm) {
        return agencyFeign.deleteVote(voteDelForm);
    }

    @PostMapping(value = "/group/keepSilent")
    @ResponseBody
    public ApiResult groupKeepSilent(@RequestBody DisturbForm chatStatusForm) {
        return agencyFeign.groupKeepSilent(chatStatusForm);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult register(@RequestBody @Valid TeacherRegisterForm registerForm, HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session) {
        ApiResult result = new ApiResult();
        registerForm.isValidMobile();
        //check验证码
        try {
            registerForm.setRole(CommonConstants.PQ_LOGIN_ROLE_TEACHER);
            result =  apiAuthService.teacherRegister(registerForm, request, response, session);
        }  catch (Exception e) {
            e.printStackTrace();
            result.setStatus(Errors.RegisterFailed.toString());
            result.setMessage("注册失败,请重试");
        }
        return result;
    }


    @RequestMapping(value = "/agency/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getAgencyList(@RequestParam(value = "name",required = false)String name){
        ApiResult<List<AgencyDto>> result = agencyFeign.getAgencyList(name);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyListDto agencyListDto = new AgencyListDto();
        agencyListDto.setList(result.getData());
        apiResult.setData(agencyListDto);
        return apiResult;
    }


    @RequestMapping(value = "/grade/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getGradeList(@RequestParam("agencyId")Long agencyId){

        ApiResult<List<AgencyDto>> result = agencyFeign.getGradeList(agencyId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyListDto agencyListDto = new AgencyListDto();
        agencyListDto.setList(result.getData());
        apiResult.setData(agencyListDto);
        return apiResult;
    }

    @RequestMapping(value = "/class/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getClassList(@RequestParam("agencyId")Long agencyId,
                                              @RequestParam("gradeId")Long gradeId){

        ApiResult<List<AgencyDto>> result =  agencyFeign.getClassList(agencyId,gradeId);
        if(!CommonErrors.SUCCESS.getErrorCode().equals(result.getStatus())){
            return result;
        }
        ApiResult apiResult = new ApiResult();
        AgencyListDto agencyListDto = new AgencyListDto();
        agencyListDto.setList(result.getData());
        apiResult.setData(agencyListDto);
        return apiResult;
    }

    @RequestMapping(value = "/update/name", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult nameUpdate(@RequestBody @Valid NameModifyForm nameModifyForm) {
        if(nameModifyForm.getRole()==CommonConstants.PQ_LOGIN_ROLE_PARENT){
            ApiResult apiResult = new ApiResult();
            apiResult.setStatus("99999");
            apiResult.setMessage("家长端无法修改姓名");
            return apiResult;
        }
        nameModifyForm.setUserId(getCurrentUserId());
        return userFeign.updateUserName(nameModifyForm);
    }

    @RequestMapping(value = "/update/schedule", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updateSchedule(@RequestBody ScheduleUpdateForm scheduleUpdateForm) {
        return agencyFeign.updateSchedule(scheduleUpdateForm);
    }

}