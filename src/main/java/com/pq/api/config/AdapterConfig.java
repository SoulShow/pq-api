package com.pq.api.config;

import com.pq.api.web.interceptor.ClientResolveInterceptor;
import com.pq.api.web.interceptor.CookieInterceptorAdapter;
import com.pq.api.web.interceptor.RequestLogInterceptor;
import com.pq.api.web.interceptor.TokenSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author liutao
 */

 @EnableWebMvc
 @Configuration
 public class AdapterConfig extends WebMvcConfigurerAdapter {

        @Bean
        ClientResolveInterceptor clientResolveInterceptor() {
            return new ClientResolveInterceptor();
        }
        @Bean
        RequestLogInterceptor requestLogInterceptor() {
            return new RequestLogInterceptor();
        }

        @Bean
        TokenSecurityInterceptor tokenSecurityInterceptor() {
            return new TokenSecurityInterceptor();
        }
        @Bean
        CookieInterceptorAdapter cookieInterceptorAdapter() {
            return new CookieInterceptorAdapter();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 多个拦截器组成一个拦截器链
            // addPathPatterns 用于添加拦截规则
            // excludePathPatterns 用户排除拦截
            registry.addInterceptor(tokenSecurityInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");

            super.addInterceptors(registry);
        }
    }
