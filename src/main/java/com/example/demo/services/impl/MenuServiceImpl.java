package com.example.demo.services.impl;

import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.services.MenuService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Menu getMenuByColumn(String column, String value) {
        return menuMapper.getMenuByColumn(column, value);
    }

    @Override
    public List<Menu> getMenusWithChild() {
        return menuMapper.getMenusWithChild();
    }
}
