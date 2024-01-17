package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.Result;
import com.example.demo.model.entity.User;
import com.example.demo.service.intf.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * 用户登录API
 * 注解@RequestMapping设定了这个Controller类所有接口的前置路径/api/user，该前置路径会和下面每一个接口的路径拼接
 * 例如下面登录接口标注的是@GetMapping("/login")，那么登录接口的实际路径是：/api/user/login
 */

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    
    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    /**
	 * 用户注册
	 *
	 * @param user   传入注册用户信息
	 * @param errors Validation的校验错误存放对象
	 * @return 注册结果
	 */
    @PostMapping("/register")
    public Result<User> register(@RequestBody @Valid User user, BindingResult errors) {
        Result<User> result;

        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }

        result = userService.register(user);
        return result;
    }

    /**
	 * 用户登录
	 *
	 * @param user    传入登录用户信息
	 * @param errors  Validation的校验错误存放对象
	 * @param request 请求对象，用于操作session
	 * @return 登录结果
	 */
    @PostMapping("/login")
    public Result<User> login(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        Result<User> result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }

        result = userService.login(user);
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }

        return result;
    }

    /**
	 * 判断用户是否登录
	 *
	 * @param request 请求对象，从中获取session里面的用户信息以判断用户是否登录
	 * @return 结果对象，已经登录则结果为成功，且数据体为用户信息；否则结果为失败，数据体为空
	 */
    @GetMapping("is-login")
    public Result<User> isLogin(HttpServletRequest request) {
        return userService.isLogin(request.getSession());
    }

    /**
	 * 用户信息修改
	 *
	 * @param user    修改后用户信息对象
	 * @param request 请求对象，用于操作session
	 * @return 修改结果
	 */
    @PatchMapping("/update")
    public Result<User> update(@RequestBody User user, HttpServletRequest request) throws Exception {
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(SESSION_NAME);
        if (sessionUser.getId() != user.getId().intValue()) {
            result.setResultFailed("当前用户和被修改的用户不一致，终止");
            return result;
        }

        result = userService.update(user);

        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.getData());
        }

        return result;
    }

    /**
	 * 用户登出
	 *
	 * @param request 请求，用于操作session
	 * @return 结果对象
	 */
    @GetMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Result<Void> result = new Result<>();
        request.getSession().setAttribute(SESSION_NAME, null);
        result.setResultSuccess("用户退出登录成功");
        return result;
    }
    
}
