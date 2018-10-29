package com.pq.api.web.interceptor;

import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContext;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.api.web.context.ClientResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截获取Client信息
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class ClientResolveInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientResolver clientResolver;

    @Autowired
    public void setClientResolver(ClientResolver clientResolver) {
        this.clientResolver = clientResolver;
    }

    /**
     * 创造当前客户端上下文
     *
     * @param request
     * @return
     */
    protected ClientContext buildClientContext(final HttpServletRequest request) {
        return new ClientContext() {

            @Override
            public Client getClient() {
                return clientResolver.resolveClient(request);
            }

            @Override
            public String toString() {
                return getClient().toString();
            }
        };
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String RESOLVED_KEY = "request-client-resolved";

        if (null == request.getAttribute(RESOLVED_KEY)) {

            ClientContextHolder.setClient(clientResolver.resolveClient(request));
            request.setAttribute(RESOLVED_KEY, Boolean.TRUE);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        if (modelAndView == null) {
//            logger.info("********************   postHandle : modelAndView is null   ********************");
//        } else if (!modelAndView.hasView()) {
//            logger.info("********************   postHandle : view is null   ********************");
//        } else {
//            if (modelAndView.getViewName() != null) {
//                logger.info("********************   postHandle : " + modelAndView.getViewName() + "   ********************");
//            } else if (modelAndView.getView() != null) {
//                logger.info("********************   postHandle : " + modelAndView.getView().toString() + "   ********************");
//            }
//        }
        if (modelAndView != null && modelAndView.hasView() && modelAndView.getViewName() != null) {
            logger.info("********************   postHandle : " + modelAndView.getViewName() + "   ********************");
        }
        super.postHandle(request, response, handler, modelAndView);

//        logger.debug("postHandle 当前是否有request code: {}", WebUtils.fetchError(request) );
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ClientContextHolder.clearContext();

//        logger.debug("afterCompletion 当前是否有request code: {}", WebUtils.fetchError(request) );

        request.setAttribute("request-done", Boolean.TRUE);
        super.afterCompletion(request, response, handler, ex);
    }

}
