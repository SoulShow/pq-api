package com.pq.api.api;

import com.pq.api.form.PasswordModifyForm;
import com.pq.api.form.UpdatePhoneForm;
import com.pq.api.service.ApiAuthService;
import com.pq.api.service.ApiUserService;
import com.pq.api.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author liutao
 * @date
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

   @Autowired
   private ApiAuthService apiAuthService;
    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getUserInfo() {
        return  apiUserService.getUserInfo(getCurrentUserId());
    }

    @RequestMapping(value = "/updatePhone/verificationCode", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult updatePhoneCode(@RequestParam(value = "account") String account,
                                              @RequestParam(value = "type") int type) {
        return apiAuthService.getCaptcha(account, type);
    }

    @RequestMapping(value = "/update/phone", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePhone(@RequestBody UpdatePhoneForm updatePhoneForm) {
        return apiUserService.updatePhone(updatePhoneForm);
    }
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePassword(@RequestBody @Valid PasswordModifyForm passwordModifyForm) {
        passwordModifyForm.setUserId(getCurrentUserId());
        return apiUserService.updatePassword(passwordModifyForm);
    }
}