package com.pq.api.type;

/**
 * 终端操作系统版本
 *
 * @author liken
 * @date 15/3/13
 */
public enum OSPlatform {

    Android,
    iOS,
    Windows,
    Unknown;

    public static OSPlatform of(String platform) {
        for (OSPlatform p : values()) {
            if (p.name().equalsIgnoreCase(platform)) {
                return p;
            }
        }

        return Unknown;
    }
}
