package com.pq.api.type;

import org.apache.commons.lang3.StringUtils;

/**
 * 客户端机型
 *
 * @author lich
 * @date 15/2/9
 */
public enum Model {
    Android, iPhone, iPad, iPod, iWatch, iTouch, iOS, Windows, Blackberry, Curl, Spider, Robot, Unknown,
    MobileBrowser, WebBrowser;

    public static Model of(String model) {
        for (Model m : values()) {
            if (m.match(model)) {
                return m;
            }
        }
        return Unknown;
    }

    public boolean match(String model) {
        if (StringUtils.isBlank(model)) {
            return false;
        }
        return StringUtils.containsIgnoreCase(model, name());
    }

    public boolean isIOS() {
        return this == iPhone || this == iPad || this == iPod || this == iWatch || this == iTouch || this == iOS;
    }

    public boolean isAndroid() {
        return this == Android;
    }

    public boolean isWindows() {
        return this == Windows;
    }

    public boolean isBlackberry() {
        return this == Blackberry;
    }

    public boolean isUnknown() {
        return this == Unknown;
    }

}
