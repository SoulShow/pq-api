package com.pq.api.web.context;

import com.pq.api.exception.AppException;
import com.pq.api.type.Model;
import com.pq.api.type.OSPlatform;
import com.pq.api.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * 解析当前客户端类型，这里会依赖 User-Agent来解析，然后得出当前请求的客户端的情况
 * 任何地方都可以调用  {@link ClientContextHolder#getClient()} 来获取当前客户端情况
 * XXX 获取真正的Client
 * <p/>
 * 这里会比较频繁的被调用，基本上一个请求会调用2次以上，去resolveClient而且应该是需要的时候就调用
 * 但是在response 输出完毕之后，就会清空
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class SimpleClientResolver implements ClientResolver {

    public static final String XDevice = "XDevice";
    public static final String XToken = "XToken";
    public static final String XRole = "XRole";
    /**
     * 客户端上传 当前版本的Header字符串 <br />
     * Android ⽰示例: HN-Salary Android/1.1.1.1101 (2.3.7 Nexus One Build/FRF91) 300x200 [Google Play]<br />
     * iOS ⽰示例: HN-Salary iOS/1.1.1.1101 (iPod touch; iPod4,1; zh-Hans) 480x320 [91]
     */
    public static final String USER_AGENT = "XUser-Agent";
    private static final String HN_SALARY = "HN-Salary";
    private static final String Android = "Android";

    private static final String iOS = "iOS";

    private static final String CLIENT_REQUEST_ATTRIBUTE_NAME = "_client_";

    private Logger logger = LoggerFactory.getLogger(getClass());

    public SimpleClientResolver() {
    }

    @Override
    public Client resolveClient(HttpServletRequest request) {

//        logger.debug("current thread id: {}, thread name: {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        //如果已经存在，解决forward过来的问题
        Client client = (Client) request.getAttribute(CLIENT_REQUEST_ATTRIBUTE_NAME);
        if (client != null) {
            return client;
        }
        client = Client.getDefault();

        //获取客户端发送的版本号，但是由于老版本没有，所以注意兼容
        String userAgent = getValueAnywhere(request, USER_AGENT);
        try {
            client.setUserAgent(userAgent);

            try {
                extractUserAgent(userAgent, client);
            } catch (RuntimeException e) {
                logger.error("解析客户端信息时，发生异常" + e.getMessage() + "当前请求路径: " + request.getServletPath() + ",当前客户端的请求头信息: " + WebUtils.printRequestHeader(request), e);
                //可能是数组越界等，解析出错，忽略，认为客户端不合法
            }

//            client.setIp(WebUtils.getClientIP(request));

            client.setDeviceId(getDeviceId(request));

            client.setToken(getClientToken(request));

            client.setUserRole(getRole(request));

            logger.trace("client: {}", client);
        } catch (AppException e) {
            logger.trace(e.getMessage(), e);
            throw e;//业务异常，直接抛出
//            WebUtils.markErrorHappend(request, e.getErrorCode().getCode());
        } finally {
            //解析只需要一次，不管出现什么情况
            request.setAttribute(CLIENT_REQUEST_ATTRIBUTE_NAME, client);
        }


        return client;
    }


    /**
     * 从请求头中获取 唯一机器码
     *
     * @param request
     * @return
     */
    private String getDeviceId(HttpServletRequest request) {
        return getValueAnywhere(request, XDevice);
    }

    /**
     * 从请求中获取当前客户端的登录Token
     *
     * @param request
     * @return
     */
    private String getClientToken(HttpServletRequest request) {

        return getValueAnywhere(request, XToken);
    }

    private int getRole(HttpServletRequest request) {

        String role = getValueAnywhere(request, XRole);
        if(role==null){
            return 0;
        }else {
            return Integer.valueOf(role);
        }
    }

    /**
     * 从参数、请求头、Cookie中获取值
     *
     * @param request
     * @param name
     * @return
     */
    private String getValueAnywhere(HttpServletRequest request, String name) {
        String value = null;

//        //再从参数获取
//        value = request.getParameter(name);
//        if (StringUtils.isNotBlank(value)) {
//            return value;
//        }


        //优先从请求头中获取
        value = request.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            return value;
        }


        //尝试从Cookie中获取
        Cookie[] cookies = request.getCookies();

        //从Cookie中获取，只有有Cookie的时候
        if ((cookies != null) && (cookies.length > 0)) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }


    /**
     * <pre>
     * User-Agent - 当前设备信息(必须)
     * 我们⽤用User-Agent来记录当前的客户端设备信息,⺫⽬目前格式要求如下:HN-Salary (标记是咱们 的⼯成长宝的客户端),设备型号(iPhone, iPad, Android),当前咱们客户端的版本号,⽐比如说 1.0.1 。
     * Android ⽰示例: HN-Salary Android/1.1.1.1101 (2.3.7 Nexus One Build/FRF91) 300x200 [Google Play] , 拆解如下:
     * 1.1.1.1101 ,版本名称,加上VersionCode
     * (2.3.7 Nexus One Build/FRF91) 当前系统版本,以及产品型号,中间空格不能省去
     * [Google Play] ⼀一定要在中括号中,这样我们好截取,这⾥里⾯面就是我们的渠道定义,是渠道ID
     * iOS ⽰示例: HN-Salary iOS/1.1.1.1101 (iPod touch; iPod4,1; zh-Hans) 480x320 [91] ,拆解如 下:
     * 1.1.1.1101 ,版本名称,加上VersionCode
     * (iPod touch; iPod4,1; zh-Hans) 第⼀一个是产品名称,第⼆二个是产品型号+当前系统信息,后⾯面是 语⾔言,由于在天际客户端已经有了,这块内容就保持不变了
     * [Google Play] ⼀一定要在中括号中,这样我们好截取,这⾥里⾯面就是我们的渠道定义,是渠道ID
     *
     * </pre>
     * <p/>
     * 抽取屏幕元素,目前写最简单的，之后再抽象到具体的策略者去 目前的策略 Android: HN-Salary Android/1.1.1.1101
     * (2.3.7 Nexus One Build/FRF91) 300x200 [Google Play] IPhone: HN-Salary
     * iOS/1.1.1.1101 (iPod touch; iPod4,1; zh-Hans) 480x320 [91]
     *
     * @param userAgent
     * @param client
     */
    protected void extractUserAgent(String userAgent, Client client) {
        if (isBlank(userAgent)) {
            return;
        }

        if (isOurClient(userAgent) && (isAndroidClient(userAgent) || isIOSClient(userAgent))) {
            dealWithMobileDevice(userAgent, client);
        } else {
            setModelIfNeccessary(client, Model.WebBrowser);
            client.setHardware(userAgent);
        }

    }

    /**
     * 是否是我们的应用，如果是的话，必须遵守我们的约定
     *
     * @param userAgent
     * @return
     */
    private boolean isOurClient(String userAgent) {
        return StringUtils.contains(userAgent, HN_SALARY);
    }

    /**
     * 根据UserAgent处理iOS客户端请求
     *
     * @param userAgent
     * @param client
     * @throws AppException
     */
    protected void dealWithMobileDevice(String userAgent, Client client) {

        String[] firstParts = split(userAgent, " ", 4);

        if (firstParts == null || firstParts.length != 4) {
            throw new IllegalArgumentException("请求头不符合格式: " + userAgent);
        }

        //上面的方法 会获得：
        //HN-Salary Android/1.1.1.1101 (2.3.7; Nexus One Build/FRF91) 300x200 [Google Play]
        //HN-Salary iOS/1.1.1.1101 (6.1.3; iPod4,1) 480x320 [Apple]
        //直接可以获取客户端名称
//        logger.debug("first parts: {}", Arrays.toString(firstParts));

        client.setName(firstParts[0]);//HN-Salary

        String platform = substringBefore(firstParts[1], "/");

        client.setModel(Model.of(userAgent));//目前无法找到 iPad iPod 这种型号
        client.setPlatform(OSPlatform.of(platform));

        //目标：Android/1.1.1.1101，version这样分割，一共会有4个部分。
        String[] verions = split(substringAfter(firstParts[1], "/"), '.');//忽略 最前面
        client.setVersion(new Version(Integer.parseInt(verions[0]), Integer.parseInt(verions[1]), Integer.parseInt(verions[2])));
        client.setVersionCode(Integer.parseInt(verions[3]));

        client.setOs(substringBetween(firstParts[2], "(", ";"));

        String lastPart = firstParts[3];//Nexus One Build/FRF91) 300x200 [Google Play]

        String separator = ") ";
        client.setHardware(substringBefore(lastPart, separator));

        String screens = substringBetween(lastPart, separator, " [");
        String[] screenSolution = split(screens, 'x');
        client.setScreenWidth(Integer.parseInt(screenSolution[0]));
        client.setScreenHeight(Integer.parseInt(screenSolution[1]));

        client.setChannel(substringBetween(lastPart, " [", "]"));
    }

    private void setModelIfNeccessary(Client client, Model model) {
        if (client.getModel() == null) {
            client.setModel(model);
        }
    }

    /**
     * 是否为Iphone客户端，目前客户端还没有变化，可以照顾到 12-06之前的所有IPhone
     * 客户端，如果有新的协议，得继续添加
     *
     * @param userAgent
     * @return
     */
    private boolean isIOSClient(String userAgent) {
        return userAgent.indexOf(iOS) > -1;
    }

    /**
     * 是否是Android客户端，可以支持到12-06
     *
     * @param userAgent
     * @return
     */
    private boolean isAndroidClient(String userAgent) {
        return userAgent.indexOf(Android) > -1;
    }

    /**
     * 分析client version
     *
     * @param clientVersion
     * @throws NullPointerException 如果clientVersion 为空
     */
    protected void extractClientVersion(String clientVersion, Client client) {
        String[] versionParts = StringUtils.split(clientVersion, ' ');
        if (versionParts != null && versionParts.length >= 3) {
            client.setModel(Model.of(versionParts[1]));

            extractVersion(versionParts[2], client);
        }
    }

    protected void extractVersion(String version, Client client) {

        if (StringUtils.startsWithIgnoreCase(version, "v")) {
            version = StringUtils.substring(version, 1);//获取 版本那块
            client.setVersionNo(version);

            String[] versionParts = StringUtils.split(version, '.');
            if (versionParts.length >= 3) {//如果版本总共有3个 //之前是==3  我改成>=3 myf 2013-7-12
                int major = parseVersionPartSafty(versionParts[0]);
                int minor = parseVersionPartSafty(versionParts[1]);
                int patch = parseVersionPartSafty(versionParts[2]);
                client.setVersion(new Version(major, minor, patch));
            }
        }
    }

    /**
     * @param part
     * @return
     */
    private int parseVersionPartSafty(String part) {
        if (StringUtils.isNotEmpty(part) && StringUtils.isNumeric(part)) {//如果是纯数字
            return Integer.valueOf(part).intValue();
        }

        return 0;
    }

    @Override
    public void setClient(HttpServletRequest request,
                          HttpServletResponse response, Client client) {
        ClientContextHolder.setClient(client);
    }

}
