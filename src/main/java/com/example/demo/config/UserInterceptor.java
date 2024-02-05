package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.Result;
import com.example.demo.service.intf.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class UserInterceptor implements HandlerInterceptor {
    
    @Autowired
    private UserService userService;

    // Controller方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!userService.isLogin(request.getHeader("Authorization")).isSuccess()) {
            Result<Void> result = new Result<>();
            result.setResultFailed("10001");
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(result);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonData);
            return false;
        }

        return true;
    }

    // Controller方法执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 整个请求完成后（包括Thymeleaf渲染完毕）
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {

    }
}
