package com.example.demo.controller;

import com.example.demo.entity.clickhouse.User;
import com.example.demo.service.clickhouse.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ck")
public class CKUserController {
    private final UserService userService;

    public CKUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/init")
    public void initDemoData() {

        userService.initDemoData();
        log.info("Demo data initialized!");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
