package com.pq.api.api;

import com.pq.api.dto.StudentLifeDto;
import com.pq.api.feign.AgencyFeign;
import com.pq.api.form.StudentModifyForm;
import com.pq.api.form.UserModifyForm;
import com.pq.api.service.ApiAgencyService;
import com.pq.api.service.ApiUserService;
import com.pq.api.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ApiResult getLifeList(@RequestParam(value = "imgs",required = false)MultipartFile[] imgs,
                                 @RequestParam("agencyClassId") Long agencyClassId,
                                 @RequestParam("studentId") Long studentId,
                                 @RequestParam(value = "title",required = false)String title,
                                 @RequestParam(value = "content",required = false)String content) {
        return apiAgencyService.createStudentLife(imgs,agencyClassId,studentId,title,content);
    }
}