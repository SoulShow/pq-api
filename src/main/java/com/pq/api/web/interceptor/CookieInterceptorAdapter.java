package com.pq.api.web.interceptor;

import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.api.web.context.SimpleClientResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对于响应结果集，直接附加请求上来的cookie
 * 这个需要在最Interceptor中的最里面，否则会导致 Client无法释放的问题
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class CookieInterceptorAdapter extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        Client client = ClientContextHolder.getClient();
        if (StringUtils.isNotEmpty(client.getToken()) && StringUtils.isNotEmpty(client.getDeviceId())) {
            response.addCookie(createCookie(SimpleClientResolver.XToken, client.getToken()));
            response.addCookie(createCookie(SimpleClientResolver.XDevice, client.getDeviceId()));
            logger.debug("添加cookie完毕 , token:{}, device:{}", client.getToken(), client.getDeviceId());
        }

        return super.preHandle(request, response, handler);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        return cookie;
    }
}
