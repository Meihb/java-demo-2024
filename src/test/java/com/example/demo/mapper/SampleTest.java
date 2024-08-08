package com.example.demo.mapper;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class SampleTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect()
    {
        System.out.println(("----- selectAll method test ------"));
        List<User>userList = userMapper.selectList(null);
        Assert.isTrue(5==userList.size(),"失败");
        userList.forEach(System.out::println);
    }
}
