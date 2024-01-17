package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.UserAPI;
import com.example.demo.mapper.UserDao;
import com.example.demo.model.dto.Result;
import com.example.demo.model.entity.User;
import com.example.demo.service.intf.UserService;
import com.example.demo.utils.ClassExamine;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jakarta.servlet.http.HttpSession;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser != null) {
            result.setResultFailed("该用户已存在");
            return result;
        }

        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        userDao.add(user);
        result.setResultSuccess("注册用户成功", user);
        return result;
    }

    @Override
    public Result<User> login(User user) {
        Result<User> result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser == null) {
            result.setResultFailed("用户不存在");
            return result;
        }

        if (!getUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            result.setResultFailed("用户名或密码错误");
            return result;
        }

        result.setResultSuccess("登录成功", getUser);
        return result;
    }

    @Override
    public Result<User> update(User user) throws Exception {
        Result<User> result = new Result<>();
        User getUser = userDao.getById(user.getId());
        if (getUser == null) {
            result.setResultFailed("用户不存在");
            return result;
        }

        if (!StrUtil.isEmpty(user.getPassword())) {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        }

        ClassExamine.objectOverlap(user, getUser);

        userDao.update(user);
        result.setResultSuccess("修改用户成功", user);
        return result;
    }

    @Override
    public Result<User> isLogin(HttpSession session) {
        Result<User> result = new Result<>();

        User sessionUser = (User) session.getAttribute(UserAPI.SESSION_NAME);
        if (sessionUser == null) {
            result.setResultFailed("用户未登录");
            return result;
        }

        User getUser = userDao.getById(sessionUser.getId());
        if (getUser == null || !getUser.getPassword().equals(sessionUser.getPassword())) {
            result.setResultFailed("用户消息无效");
            return result;
        }

        result.setResultSuccess("用户已登录", getUser);
        return result;
    }
    
}
