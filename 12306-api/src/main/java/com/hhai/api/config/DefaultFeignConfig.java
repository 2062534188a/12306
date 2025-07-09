package com.hhai.api.config;

import com.hhai.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {

    @Bean
    public RequestInterceptor userInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 获取登录用户
                Long userId = UserContext.getUser();
                System.out.println("Feign Interceptor - UserId: " + userId); // 调试日志
                if(userId == null) {
                    // 如果为空则直接跳过
                    return;
                }
                // 如果不为空则放入请求头中，传递给下游微服务
                template.header("user-info", userId.toString());
            }
        };
    }
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
//    @Bean
//    public ItemClientFallback itemClientFallback(){
//        return new ItemClientFallback();
//    }
}
