package com.pq.api.utils;

/**
 * 时间来源接口，定义返回时间的毫秒书
 *
 * @author gogo
 */
public interface TimeSource {

    /**
     * 返回当前时间的毫秒数
     *
     * @return 当前时间的毫秒
     */
    long asMillis();
}
