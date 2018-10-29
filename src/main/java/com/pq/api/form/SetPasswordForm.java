package com.pq.api.form;

import com.pq.common.util.OtherUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 忘记密码的表单
 *
 * @author liutao
 * @date 15/6/3
 */
public class SetPasswordForm implements Serializable {
    private String account;
    private String password;
    private String repPassword;

    @NotBlank(message = "手机号码不能为空")
    @NotNull(message = "手机号码必须填写")
    @Size(min = 11, max = 11, message = "请输入11位手机号码")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码必须填写")
    @Size(min = 6, max = 12, message = "密码必须是6-12位")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NotBlank(message = "确认密码不能为空")
    @NotNull(message = "确认密码必须填写")
    @Size(min = 6, max = 12, message = "确认密码必须是6-12位")
    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }

    /**
     * 依赖验证流程，判断 密码 跟 确认密码一致
     *
     * @return
     */
    @AssertTrue(message = "新密码与确认密码不一致")
    public boolean isPasswordMatch() {
        //进行严格的字符串比较，是否一致
        return StringUtils.equals(password, repPassword);
    }

    @AssertTrue(message = "手机号码不符合格式")
    public boolean isValidMobile() {
        return OtherUtil.verifyPhone(account);
    }
}

