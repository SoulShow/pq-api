package com.pq.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;


/**
 * @author liutao
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3000, redisNamespace = "redis-session")
public class HttpSessionConfig {
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}
