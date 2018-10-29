package com.pq.api.web.context;

/**
 * 客户端上下文
 *
 * @author liken
 * @date 15/3/13
 */
public interface ClientContext {
    /**
     * 获取当前客户端信息
     *
     * @return
     */
    Client getClient();

}
