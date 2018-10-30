package com.pq.api.api;


import com.pq.api.exception.AppErrorCode;
import com.pq.api.service.HystrixService;
import com.pq.api.type.Errors;
import com.pq.api.utils.Result;
import com.pq.api.utils.WebUtils;
import com.pq.api.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private HystrixService hystrixService;

	@RequestMapping(value = "/test",method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUsers() {
		ApiResult result = new ApiResult();
		result.setData(hystrixService.getOrderPageList());
		return result;
	}


	@RequestMapping(value = "/test1",method = RequestMethod.GET)
	public String test() {
		return "test1";
	}

	@RequestMapping(value = "failed")
	@ResponseBody
	public ApiResult failed(HttpServletRequest request) {
		ApiResult result = new ApiResult();
		AppErrorCode error = WebUtils.fetchError();

		if (error != null) {
			result.setStatus(error.getStatus());
			result.setMessage(error.getMessage());
		} else {
			result.setStatus(Errors.TokenInvalid.toString());
			result.setMessage("TOKEN失效");
		}
		return result;
	}

}