package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注册
 */
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

    /**
	 * 把我们定义的拦截器类注册为Bean
	 */
    @Bean
    public HandlerInterceptor getInterceptor() {
        return new UserInterceptor();
    }

    /**
	 * 添加拦截器，并配置拦截地址
	 */
    @Override
    public void addInterceptors(InterceptorRegistry register) {
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/api/user/login");
        pathPatterns.add("/api/user/register");
        register.addInterceptor(getInterceptor()).excludePathPatterns(pathPatterns);
    }
}
