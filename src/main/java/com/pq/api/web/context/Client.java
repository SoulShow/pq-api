package com.pq.api.web.context;


import com.pq.api.dto.LoginUser;
import com.pq.api.type.Machine;
import com.pq.api.type.Model;
import com.pq.api.type.OSPlatform;

/**
 * 保存当前请求的客户端信息,当前屏幕大小，分辨率，版本等等
 *
 * @author liken
 * @date 15/3/13
 */
public class Client implements Cloneable {

    private static final Version EMPTY_VERSION = new Version();
    private static final Client EMPTY_CLIENT = new Client(EMPTY_VERSION, "unknown", Model.Unknown);

    private String name;//当前客户端名称，对应 HN-Saraly，表明当前API请求的客户端名称。

    private Version version;//版本

    private int versionCode;//版本编译号是多少，我们内部通过这个控制升级流程

    private String hardware;// 品牌，具体的设备型号 iphone4s等修改，针对苹果的型号，hardware model
    private Model model;// 终端是什么，Android 还是 iPhone 还是 iPad 或者 iTouch 等

    private int screenWidth;//屏幕宽度
    private int screenHeight;//屏幕高度
    private String versionNo;

    /**
     * 操作系统版本
     */
    private String os;

    /**
     * 操作系统版本
     */
    private OSPlatform platform = OSPlatform.Unknown;

    //客户端信息
    private String userAgent;

    private String channel;//市场渠道

    private String ip;

    /**
     * 当前的设备ID，这个对于设备来说，应该是唯一的
     */
    private String deviceId;

    private String token;

    //当前登录用户ID
    private String userId;

    private RequestContext requestContext;

    /**
     * 登录用户凭证
     */
    private Object loginUser;

    private String gtClientId;


    public Client() {
    }

    public Client(Version version, String hardware, Model model) {
        super();
        this.version = version;
        this.hardware = hardware;
        this.model = model;
    }

    /**
     * 获得一个默认的Client对象
     *
     * @return
     */
    public static Client getDefault() {
        return (Client) EMPTY_CLIENT.clone();
    }

    /**
     * 这个结果集可能为空，因为访问上来的客户端，可能不一定遵照我们的约定访问
     *
     * @return
     */
    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * 获得客户端的品牌，如果是手机，就是手机型号，如三星等
     * 如果是浏览器，是浏览器版本等
     * 如果是API，则是API标识等
     *
     * @return
     */
    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    protected Object clone() {
        try {
            return (Client) super.clone();
        } catch (CloneNotSupportedException e) {
            // assert false.
            // client = new Client();
            throw new InternalError();
        }
    }

    /**
     * 获取客户端希望的资源屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * 获取客户端希望的资源屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * 是否需要屏幕属性，比如说php,网页客户端，不需要
     *
     * @return
     */
    public boolean hasScreen() {
        return this.screenHeight > 0 && this.screenWidth > 0 &&
                isMobile();
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isMobile() {
        return this.model == Model.Android || this.model == Model.iPhone ||
                this.model == Model.iPad || this.model == Model.iTouch ||
                this.model == Model.iPod ||
                this.model == Model.MobileBrowser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String clientIP) {
        this.ip = clientIP;
    }

    /**
     * 返回客户端唯一标记，永远不为空
     *
     * @return
     */
    public String getDeviceId() {
        return deviceId == null ? "" : deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取客户端名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OSPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(OSPlatform platform) {
        this.platform = platform;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    /**
     * 作用就是翻译一些机器语机型，增强可读性用的。
     * <p/>
     * 比如说iPod4,1  这个是苹果自定义的产品型号，对应的可读性是：iPod touch 4G
     *
     * @return
     */
    public String asMachine() {
        Machine machine = Machine.INSTANCE;
        if (machine.hasName(hardware)) {
            return machine.getMachineName(hardware);
        }
        return hardware;
    }

    /**
     * 用于Json描述
     *
     * @return
     */
    public String getDescription() {
        return toString();
    }

    /**
     * 需要的时候，创建
     *
     * @return
     */
    public RequestContext getRequestContext() {
        if (this.requestContext == null) {
            this.requestContext = new RequestContext();
        }
        return requestContext;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Object loginUser) {
        this.loginUser = loginUser;

        if (this.loginUser != null && this.loginUser instanceof LoginUser) {
            LoginUser loginedUser = (LoginUser) this.loginUser;
            setUserId(loginedUser.getUserId());
        }
    }

    public boolean isLogined() {
        return getLoginUser() != null;//当前登录用户信息不为空
    }

    @Override
    public String toString() {
        return "Client [name=" + name + ", version=" + version
                + ", versionCode=" + versionCode + ", hardware=" + hardware
                + ", model=" + model + ", screenWidth=" + screenWidth
                + ", screenHeight=" + screenHeight + ", os=" + os
                + ", platform=" + platform + ", userAgent=" + userAgent
                + ", channel=" + channel + ", ip=" + ip + ", deviceId="
                + deviceId + ", token=" + token + ", userId=" + userId
                + ", requestContext=" + requestContext + ", loginUser="
                + loginUser + "]";
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getGtClientId() {
        return gtClientId;
    }

    public void setGtClientId(String gtClientId) {
        this.gtClientId = gtClientId;
    }
}
