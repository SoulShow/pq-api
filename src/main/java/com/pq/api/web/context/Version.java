package com.pq.api.web.context;

import com.pq.common.util.StringUtil;

import java.util.Comparator;

/**
 * 客户端的版本
 *
 * @author liken
 * @date 15/3/13
 */
public class Version implements Comparator<Version>, Comparable<Version> {
    public static final int UNKOWN = -1;

    private int major = UNKOWN; // 主版本号, 默认是负数
    private int minor = UNKOWN; // 次版本号, 默认是负数
    private int patch = UNKOWN; // 补丁级别, 默认是负数

    public Version() {
    }

    public Version(int major, int minor, int patch) {
        super();
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    public Version(String version) {
        super();
        if (!StringUtil.isBlank(version)) {
            String[] versions = version.trim().split("\\.");
            if (versions.length >= 3) {
                this.major = Integer.parseInt(versions[0]);
                this.minor = Integer.parseInt(versions[1]);
                this.patch = Integer.parseInt(versions[2]);
            }
        }

    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", major, minor, patch);
    }

    /**
     * 判断版本是否在 目标版本之前(比当前版本要旧)
     *
     * @param version 被比较的版本
     * @return
     */
    public boolean isPrevious(Version version) {
        return this.compareTo(version) < 0;
    }

    /**
     * 判断版本是否在 目标版本之前
     *
     * @param version 被比较的版本 一致
     * @return
     */
    public boolean isSame(Version version) {
        return this.compareTo(version) == 0;
    }

    /**
     * 判断版本是否在 目标版本之后
     *
     * @param version 被比较的版本
     * @return true 表示 目标版本 要比当前操作版本要新，
     */
    public boolean isLater(Version version) {
        return this.compareTo(version) > 0;
    }

    @Override
    public int compare(Version o1, Version o2) {
        if (o1 == o2) {//解决空指针的问题，并且同引用
            return 0;
        }

        if (o1 != null) {
            return o1.compareTo(o2);
        }

        if (o2 != null) {
            return -(o2.compareTo(o1));
        }
        //在这里，两个都不为空

        return 0;
    }

    @Override
    public int compareTo(Version vThat) {
        final int PREVIOUS = -1;//之前的版本
        final int EQUAL = 0;//同一版本
        final int LATER = 1;//之后的版本

        if (this == vThat) {
            return EQUAL;
        }

        if (vThat == null) {
            return PREVIOUS;
        }

        if (this.major != vThat.major) {
            return this.major < vThat.major ? PREVIOUS : LATER;
        }

        if (this.minor != vThat.minor) {
            return this.minor < vThat.minor ? PREVIOUS : LATER;
        }

        if (this.patch != vThat.patch) {
            return this.patch < vThat.patch ? PREVIOUS : LATER;
        }

        return EQUAL;//FindBugs 找到的BUG 并且提出的解决方案
    }
}
