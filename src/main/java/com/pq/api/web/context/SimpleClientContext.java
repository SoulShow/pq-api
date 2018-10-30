package com.pq.api.web.context;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 当前客户端上下文承载
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class SimpleClientContext implements ClientContext {

    private final Client client;

    public SimpleClientContext(Client client) {
        Assert.notNull(client, "Client must not be null");
        this.client = client;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return this.client.toString();
    }


}
