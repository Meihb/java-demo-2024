package com.example.demo.service.clickhouse;


import com.example.demo.entity.clickhouse.User;
import com.example.demo.respository.clickhouse.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initDemoData() {
        userRepository.createTable();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("User_" + i);
            user.setAge(20 + i);
            user.setCreatedAt(LocalDateTime.now().format(formatter));
            userRepository.insertUser(user);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
