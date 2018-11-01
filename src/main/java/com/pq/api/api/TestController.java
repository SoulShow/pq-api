package com.pq.api.api;


import com.pq.api.vo.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/test",method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getUsers() {
		ApiResult result = new ApiResult();
		return result;
	}


	@RequestMapping(value = "/test1",method = RequestMethod.GET)
	public String test() {
		return "test1";
	}



}