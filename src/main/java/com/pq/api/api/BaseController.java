package com.pq.api.api;

import com.pq.api.dto.LoginUser;
import com.pq.api.exception.AppException;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.common.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author liutao
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Random random = new Random();

    /**
     * 获取当前登录用户ID
     * XXX 修复当前的设置，变成动态获取，目前是伪实现
     *
     * @return
     */
    protected String getCurrentUserId() {

        Client client = ClientContextHolder.getClient();

        if (client.isLogined()) {
            LoginUser loginUser = (LoginUser) client.getLoginUser();
            return loginUser.getUserId();
        }
        //默认返回伪装的UID
        return null;
    }

    /**
     * 获取当前请求的Client对象
     *
     * @return
     */
    protected Client getClient() {
        return ClientContextHolder.getClient();
    }
}

