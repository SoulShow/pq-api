package com.pq.api.web.interceptor;

import com.pq.api.utils.SystemTime;
import com.pq.api.utils.Utils;
import com.pq.api.utils.WebUtils;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.api.web.context.RequestContext;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liken
 * @date 15/3/13
 */
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
    public static final String REQUEST_LOG_PATTERN = ">>>>> RID=%s | %s | UID=%s | %s %s %s | RequestBody=%s | Token=%s | Request Header = [%s]";
    public static final String RESPONSE_LOG_PATTERN = "<<<<< RID=%s | %s | Time(ms)=%d Len=%d FL=%s| UID=%s | RESULT %s | Token=%s | R=%s";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String X_REQUEST_UUID = "X-REQUEST-ID";

    private final Logger apiLogger = LoggerFactory.getLogger(getClass());
//    private static final Logger apiLogger = LoggerFactory.getLogger("api_log");

    /**
     * 在经过Spring Controller层处理的请求中，会启用该方法。但在下面的情况下，该方法不会被启用:
     * <ul>
     * <li>拦截器, {@link javax.servlet.Filter}, 换句话说，目前的方法不会覆盖到 /security_login 等URL中,但可以照顾到 /security_login
     * 处理之后，内部forward到 /login/success 的流程中去 </li>
     * </ul>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Client client = ClientContextHolder.getClient();

        RequestContext context = client.getRequestContext();

        context.markRequestStarted();//标记请求开始

        if (!context.hasRequestUUID()) {
            //只有没有分配的时候，才去分配临时的Request ID
            String rid = RandomStringUtils.randomAlphanumeric(20);//随机生成字符串，做为当前的请求的唯一ID
            context.setRequestUUID(rid);
        }

        if (!context.hasRequestHeaders()) {
            //打印出请求的Header
            String headers = WebUtils.printRequestHeader(request);
            context.setRequestHeaders(headers);
        }

        if (!context.hasRequestParams()) {

            //2. 请求参数
            String paramStr = WebUtils.getParameterStr(request);

            context.setRequestParams(paramStr);//存储参数
        }

        //1. 请求的 URL
        context.addRequestPath(request.getServletPath());

        response.setHeader(X_REQUEST_UUID, context.getRequestUUID());
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");//目前是为了wiki加载更方便

//        ClientContextHolder.setClient(client);

        //可能日志会有多行，但是能够看到一条完整的记录。
        apiLogger.debug(String.format(
                REQUEST_LOG_PATTERN,
                context.getRequestUUID(), client.getIp(), client.getUserId(),
                request.getMethod(), context.getRequestPaths(), context.getRequestParams(),
                context.getRequestBody(),
                client.getToken(), context.getRequestHeaders()
        ));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        Client client = ClientContextHolder.getClient();

        RequestContext context = client.getRequestContext();

        //XXX 目前如果是为了json的展示，在日志中，会导致无法正常看到一个访问周期。
        //之所以在这个位置，是因为在preHandler的时候，还还没有处理json。
        if ("POST".equals(request.getMethod())) {

            apiLogger.debug(String.format(
                    REQUEST_LOG_PATTERN,
                    context.getRequestUUID(), client.getIp(), client.getUserId(),
                    request.getMethod(), context.getRequestPaths(), context.getRequestParams(),
                    context.getRequestBody(),
                    client.getToken(), context.getRequestHeaders()
            ));
        }

        context.setRequestPrinted(true);
    }

    /**
     * 可以查看 {@link ClientResolveInterceptor#afterCompletion(HttpServletRequest, HttpServletResponse, Object, Exception)}
     * 方法，里面已经设置请求完毕
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        if (request.getAttribute("request-done") != null) {
            return;//如果已经处理完毕了，就不需要继续了
        }

        Client client = ClientContextHolder.getClient();
        RequestContext context = client.getRequestContext();

        if (!context.isRequestPrinted()) {//如果发生异常了。在这里打印信息
            //之所以在这个位置，是因为在preHandler的时候，还还没有处理json。
            apiLogger.debug(String.format(
                    REQUEST_LOG_PATTERN,
                    context.getRequestUUID(), client.getIp(), client.getUserId(),
                    request.getMethod(), context.getRequestPaths(), context.getRequestParams(),
                    context.getRequestBody(),
                    client.getToken(), context.getRequestHeaders()
            ));
            context.setRequestPrinted(true);
        }

        // 打印 请求的响应日志内容，需要提前设置 {@link #FOXFLY_REMEMBER_JSON_LOG_CONTENT}
        // 属性到请求对象中
        String requestPath = context.getRequestPaths();

        long timeCast = SystemTime.asMillis() - context.getRequestStartAt();

        apiLogger.debug(String.format(
                RESPONSE_LOG_PATTERN,
                context.getRequestUUID(), client.getIp(), timeCast,
                context.getResponseContentLength(), Utils.humanReadableByteCount(context.getResponseContentLength()),
                client.getUserId(), requestPath, client.getToken(), context.getResponseBody()
        ));
    }
}
