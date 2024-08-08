package com.example.demo.service.impl;

import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.service.MenuService;
import jakarta.annotation.Resource;
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
