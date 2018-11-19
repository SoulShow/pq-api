package com.pq.api.feign;


import com.pq.api.dto.*;
import com.pq.api.feign.input.AgencyUserRegisterInput;
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
    ApiResult createUser(@RequestBody AgencyUserRegisterInput userRegisterInput);
}
