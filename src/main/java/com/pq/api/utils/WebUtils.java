package com.pq.api.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pq.api.exception.AppErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * @author liken
 * @date 15/3/13
 */
@Component
public class WebUtils {

    /**
     * 用于请求内部的KEY，专门用来保存错误异常的Code
     */
    public static final String HN_APP_EXCEPTION_CODE = "hn.app.exception.code";
    private final static Logger logger = LoggerFactory.getLogger(WebUtils.class);
//    private static final String EMAIL_PATTERN = "/^(.+)@(.+)$/";

    //    private static String webBasePath;
    private static final String UTF_8 = "UTF-8";
    /**
     * 用于组装URL时，应该排除的URL前缀
     */
    private static List<String> EXECLUSIVE_ASSEMBLE_PREFIXES = Values.Instance.getGlobalConfig().getStringList("webAssembleExeclusivePrefix");
//
//    @Value("${api.base.path}")
//    public void setWebBasePath(String webBasePath) {
//        logger.debug("初始化 目前服务器的 基准路径是: {}", webBasePath);
//        WebUtils.webBasePath = webBasePath;
//    }
//

    /**
     * 找到邮件的域名,如果有的话
     *
     * @param emailAddress
     * @return 如果找不到域名，或者是非法的邮件格式，则返回null
     */
    public static String getEmailDomain(String emailAddress) {
        int index = 0;
        if (emailAddress == null || emailAddress.length() <= 3
                || (index = emailAddress.indexOf('@')) <= 0) {
            return null;
        }

        return emailAddress.substring(index + 1);
    }

    public static boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");//之所以不用正则判断，是因为逻辑没有那么复杂，并且这样性能会比那个高
        return tokens.length == 2 && StringUtils.isNotBlank(tokens[0])
                && StringUtils.isNotBlank(tokens[1]);
    }

    /**
     * 统一的解析上行参数，以逗号隔开
     *
     * @param ids 需要被分割的参数
     * @return 返回分割之后的集合
     * 如果 ids==null，返回空的集合
     * 如果 不为空，则返回所有经过 逗号 分割的字符串集合
     */
    @SuppressWarnings("unchecked")
    public static List<String> parseParams(String ids) {
        if (StringUtils.isBlank(ids)) {//如果参数为空，直接返回空集合
            return Lists.newArrayList();
        } else {
            String[] idArray = StringUtils.split(ids, ',');//已经判断了参数为空的情况，所以这里不会为空
            final List<String> idLists = Lists.newArrayList();
            for (String id : idArray) {
                if (StringUtils.isNotBlank(id)) {
                    idLists.add(id.trim());
                }
            }
            return idLists;
        }

    }

    public static List<Integer> parseIntegerParams(String ids) {
        if (StringUtils.isBlank(ids)) {//如果参数为空，直接返回空集合
            return Lists.newArrayList();
        } else {
            String[] idArray = StringUtils.split(ids, ',');//已经判断了参数为空的情况，所以这里不会为空
            final List<Integer> idLists = Lists.newArrayList();
            for (String id : idArray) {
                if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
                    idLists.add(Integer.valueOf(id.trim()));
                }
            }
            return idLists;
        }
    }

    public static List<Long> parseLongParams(String ids) {
        if (StringUtils.isBlank(ids)) {//如果参数为空，直接返回空集合
            return Lists.newArrayList();
        } else {
            String[] idArray = StringUtils.split(ids, ',');//已经判断了参数为空的情况，所以这里不会为空
            final List<Long> idLists = Lists.newArrayList();
            for (String id : idArray) {
                if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
                    idLists.add(Long.valueOf(id.trim()));
                }
            }
            return idLists;
        }
    }

    public static Set<Long> parseLongParams2(String ids) {
        Set<Long> result = Sets.newHashSet();
        if (StringUtils.isBlank(ids)) {//如果参数为空，直接返回空集合
            return result;
        } else {
            String[] idArray = StringUtils.split(ids, ',');//已经判断了参数为空的情况，所以这里不会为空
            for (String id : idArray) {
                if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
                    result.add(Long.valueOf(id.trim()));
                }
            }
            return result;
        }
    }

    public static List<Double> parseDoubleParams(String ids) {
        if (StringUtils.isBlank(ids)) {//如果参数为空，直接返回空集合
            return Lists.newArrayList();
        } else {
            String[] idArray = StringUtils.split(ids, ',');//已经判断了参数为空的情况，所以这里不会为空
            final List<Double> idLists = Lists.newArrayList();
            for (String id : idArray) {
                if (StringUtils.isNotBlank(id)) {
                    Double d = null;
                    try {
                        d = Double.valueOf(id.trim());
                    } catch (NumberFormatException e) {
                        break;
                    }
                    idLists.add(d);
                }
            }
            return idLists;
        }
    }

    public static Boolean parseBoolean(String parameter) {
        if ((!(parameter.equalsIgnoreCase("true")))
                && (!(parameter.equalsIgnoreCase("on")))
                && (!(parameter.equalsIgnoreCase("yes")))
                && (!(parameter.equals("1"))))
            return Boolean.valueOf(false);
        return Boolean.valueOf(true);
    }

    @SuppressWarnings("unchecked")
    public static String printRequestHeader(HttpServletRequest request) {
        StringBuilder buf = new StringBuilder(200);
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            buf.append(key).append(" => ").append(request.getHeader(key)).append(", ");
        }
        return buf.toString();
    }


    /**
     * 将请求对象中的 参数 以字符串形式输出
     *
     * @param request
     * @return
     * @author royall
     * @since 2011-8-30
     */
    public static String getParameterStr(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        StringBuilder pstr = new StringBuilder("{");

        while (paramNames.hasMoreElements()) {
            String key = (String) paramNames.nextElement();
            String[] value = request.getParameterValues(key);//这样才可以使用Encode的回调
            pstr.append(key).append("=[");
            pstr.append(Joiner.on(',').join(value).toString());
            pstr.append("], ");
        }

        pstr.append("}");
        return pstr.toString();
    }

    /**
     * 将请求对象中的 Cookie 以字符串形式输出
     *
     * @param request
     * @return
     * @author royall
     * @since 2011-8-30
     */
    public static String getCookieStr(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        StringBuilder buf = new StringBuilder('{');
        final String d = ", ";
        for (int i = 0; i < cookies.length; i++) {
            String name = cookies[i].getName();
            String value = cookies[i].getValue();
            buf.append(name).append('=').append(value);
            if (i < cookies.length - 1) {
                buf.append(d);
            }
        }
        buf.append('}');
        return buf.toString();
    }

    /**
     * 获得请求对象中客户端IP地址
     *
     * @param request
     * @return
     * @author royall
     * @since 2011-8-30
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");// X-Forwarded-For
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String encodeURL(String source) {
        try {
            return URLEncoder.encode(source, UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("无法URL Encode字符串: " + source);
        }
    }

    /**
     * 获取服务的基本路径
     *
     * @return
     */
    public static String getBasePath() {

        ServletRequestAttributes webRequest = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (webRequest == null || webRequest.getRequest() == null) {
            //由于没有，则返回配置好的，达到兼容
            throw new IllegalStateException("只有当前有Request对象时才能够用这个方法");
        }
        return getBasePath(webRequest.getRequest());
    }

    /**
     * 获取服务的基本路径
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {

        String url = getFromNativeRequest(request);
        return url;
    }

    /**
     * 从Java Servlet 本身获取路径属性
     *
     * @param request
     * @return
     */
    private static String getFromNativeRequest(HttpServletRequest request) {
        int port = request.getServerPort();
        StringBuilder containUrl = new StringBuilder(60);
        containUrl.append(request.getScheme()).append("://");
        containUrl.append(request.getServerName());
        //不添加默认端口
        if (port != 80 && port != 443) {
            containUrl.append(':').append(port);
        }

        //如果经过Nginx配置
        String proxiedContextPath = request.getHeader("X-Context-Path");
        if (proxiedContextPath != null) {
            containUrl.append(proxiedContextPath);
        } else {
            containUrl.append(request.getContextPath());
        }

        containUrl.append('/');
        String url = containUrl.toString();
        return url;
    }

    /**
     * 根据环境，组装下载路径。
     * 默认是相对路径，如果是 网络HTTP、HTTPS、FTP等协议开头的，则直接
     * 使用原生的地址
     *
     * @return
     */
    public static String getAssembled(String path) {

        if (path == null) {
            return null;
        }

        for (String prefix : EXECLUSIVE_ASSEMBLE_PREFIXES) {//找到
            if (StringUtils.startsWithIgnoreCase(path, prefix)) {
                return path;
            }
        }

        return getBasePath() + path;
    }
    public static String getAssembled(String path, boolean forceHttp) {
        String result = getAssembled(path);
//        if (forceHttp && result != null) {
//            if (result.startsWith("https")) {
//                result = result.replace("https", "http");
//            }
//        }
        return result;
    }

    /**
     * 内部请求跳转工工具
     *
     * @param request
     * @param response
     * @param code       错误代码
     * @param forwardUri 转发到什么URI中去
     * @throws IOException
     * @throws ServletException
     */
    public static void forwardToError(HttpServletRequest request, HttpServletResponse response,
                                      Integer code, String forwardUri) throws ServletException, IOException {
        request.setAttribute(HN_APP_EXCEPTION_CODE, code);
        request.getRequestDispatcher(forwardUri).forward(request, response);
    }

    /**
     * 标记错误已经发生过
     *
     * @param request
     * @param code
     * @throws ServletException
     * @throws IOException
     */
    public static void markErrorHappend(HttpServletRequest request,
                                        Integer code) {
        request.setAttribute(HN_APP_EXCEPTION_CODE, code);
    }

    /**
     * 从内部请求对象中，获取当前错误代码对象
     *
     * @return
     */
    public static AppErrorCode fetchError() {
        ServletRequestAttributes webRequest = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return fetchError(webRequest.getRequest());
    }

    /**
     * 从内部请求对象中，获取当前错误代码对象
     *
     * @param request 请求对象
     * @return
     */
    public static AppErrorCode fetchError(HttpServletRequest request) {

        if (request != null) {
            Object oCode = request.getAttribute(HN_APP_EXCEPTION_CODE);
            if (oCode != null && oCode instanceof Integer) {
                Integer code = (Integer) oCode;
                return AppErrorCode.error(code);
            }
        }

        return null;//默认是 没有有效的token
    }

    /**
     * 获取当前的Scheme
     *
     * @return
     */
    public static String getScheme() {
        ServletRequestAttributes webRequest = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return webRequest.getRequest().getScheme();
    }

    /**
     * 方便JSP调用
     *
     * @return
     */
    public static String scheme() {
        return getScheme();
    }

}
