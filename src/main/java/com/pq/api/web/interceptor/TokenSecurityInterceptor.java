package com.pq.api.web.interceptor;

import com.pq.api.dto.LoginUser;
import com.pq.api.type.Errors;
import com.pq.api.utils.ConstansAPI;
import com.pq.api.utils.WebUtils;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.common.constants.CacheKeyConstants;
import com.pq.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 根据客户端的Token信息进行安全控制
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class TokenSecurityInterceptor extends HandlerInterceptorAdapter {

    private static final String X_LAST_USED = "X-Last-Used";
    private static Logger logger = LoggerFactory.getLogger(TokenSecurityInterceptor.class);
    /**
     * 所有不需要过滤的匹配，需要是ant风格
     */
    private List<String> exclusivePath;

    private PathMatcher pathMatcher = new AntPathMatcher();

    private long loginTimeOut = 30 * 60 * 1000;
    @Autowired
    private RedisTemplate redisTemplate;

    public void setExclusivePath(List<String> exclusivePath) {
        this.exclusivePath = exclusivePath;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Client client = ClientContextHolder.getClient();
        logger.info("forward request : " + client.getRequestContext().isForwardRequest());
        if (client.getRequestContext().isForwardRequest()) {
            return true;
        }

        if (!shouldApplyTo(request, response)) {
            return true;
        }

        if (client.isLogined()) {
            return true;
        }


        String token = client.getToken();
        String deviceId = client.getDeviceId();

        HttpSession session = request.getSession();

        String userId = (String) session.getAttribute(ConstansAPI.SESSION_USER_ID_KEY);

        long lastAccessedTime = session.getLastAccessedTime();
        long currentTime = System.currentTimeMillis();

        logger.info("\n*************** token : " + token + "\n*************** session : " + session.getId());


        //验证token是否相同

        String existToken =(String)redisTemplate.opsForValue().get(CacheKeyConstants.USER_SESSION_MAP_KEY_PREFIX + userId);
        if(StringUtil.isEmpty(token)){
            forwardIfAuthFailed(request, response, Errors.LoginRequired);
            return false;
        }

        if(!token.equals(existToken)){
            forwardIfAuthFailed(request, response, Errors.TokenInvalid);
            return false;
        }
        if (userId == null || (currentTime - lastAccessedTime) >= loginTimeOut) {
            forwardIfAuthFailed(request, response, Errors.LoginRequired);
            return false;
        }
        logger.info("userId : " + userId);
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(token);
        loginUser.setUserId(userId);

        client.setLoginUser(loginUser);

        return true;

    }

    /**
     * 是否在当前请求中 ，应用该Handler，如果以后需求扩展.
     *
     * @param request
     * @param response
     * @return
     */
    public boolean shouldApplyTo(HttpServletRequest request, HttpServletResponse response) {
        boolean applyTo = true;//默认都是需要处理的

        if (exclusivePath != null) {//如果path 不为空, urlPatterns也不会
            String path = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
            for (String ePath : exclusivePath) {
                boolean uriExcluded = pathMatcher.match(ePath, path);//该uri是否是被屏蔽的
                if (uriExcluded) {//如果是被屏蔽的URI，则不应该被 记录日志
                    applyTo = false;
                }
            }
        }

        return applyTo;
    }


    private void forwardIfAuthFailed(HttpServletRequest request,
                                     HttpServletResponse response, Integer code) throws ServletException, IOException {
        String contentType = request.getHeader("accept");
        if (contentType != null) {
            String[] accepts = StringUtils.split(contentType, ',');

            for (String accept : accepts) {
                try {
                    MediaType mediaType = MediaType.parseMediaType(accept);

                    //如果是网页请求，则直接转发到登录页面
                    if (MediaType.TEXT_HTML.includes(mediaType)) {
                        request.setAttribute("next", getCurrentURL(request));
                        WebUtils.forwardToError(request, response, code, "/auth/login/jump");
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    logger.debug("无法解析Accept请求头", ex);
                }
            }
        }

        contentType = request.getContentType();

        if (contentType != null) {
            try {
                MediaType mediaType = MediaType.parseMediaType(contentType);
                //如果是网页请求，则直接转发到登录页面
                if (MediaType.TEXT_HTML.includes(mediaType)) {
                    request.setAttribute("next", getCurrentURL(request));
                    WebUtils.forwardToError(request, response, code, "/auth/login/jump");
                    return;
                }
            } catch (IllegalArgumentException ex) {
                logger.debug("无法解析Content-Type请求头", ex);
            }
        }

        WebUtils.forwardToError(request, response, code, "/failed");
    }

    private String getCurrentURL(HttpServletRequest req) throws UnsupportedEncodingException {
        String pathInfo = req.getPathInfo();
        String queryString = req.getQueryString();

        StringBuilder url = new StringBuilder();
        url.append(WebUtils.getBasePath(req)).append(req.getServletPath());
        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return URLEncoder.encode(url.toString(), "UTF-8");
    }

    public long getLoginTimeOut() {
        return loginTimeOut;
    }

    public void setLoginTimeOut(long loginTimeOut) {
        this.loginTimeOut = loginTimeOut;
    }
}
