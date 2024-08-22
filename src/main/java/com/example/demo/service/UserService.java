package com.example.demo.service;

import com.example.demo.entity.User;

import java.time.ZonedDateTime;

public interface UserService {
    User findByUsername(String username);

    String makePassword(String password, ZonedDateTime create_time);
}
