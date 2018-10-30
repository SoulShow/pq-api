package com.pq.api.web.context;

import org.springframework.stereotype.Component;

/**
 * 客户端上下文
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public interface ClientContext {
    /**
     * 获取当前客户端信息
     *
     * @return
     */
    Client getClient();

}
