package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.MD5Util;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return new User().selectOne(queryWrapper);
    }

    @Override
    public String makePassword(String password, ZonedDateTime create_time) {
        long createTimestamp = create_time.toEpochSecond();
        String shadow = MD5Util.MD5Encode(MD5Util.MD5Encode(password, "UTF-8") + createTimestamp, "UTF-8");
        shadow = shadow.substring(0, 31);
        return "s" + shadow;
    }
}
