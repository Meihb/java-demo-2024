package com.example.demo.config;

import com.example.demo.interceptor.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserAuthInterceptor userAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor)
                .order(1)// 设置优先级，较小的数字代表更高的优先级
                .addPathPatterns("/**") // 你可以指定拦截路径
                .excludePathPatterns("/assets/**", "/js/**", "/porto/**","/swagger-ui/**")
        ;
    }

}
