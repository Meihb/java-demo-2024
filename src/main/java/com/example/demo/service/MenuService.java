package com.example.demo.service;

import com.example.demo.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu getMenuByColumn(String column,String value);
    List<Menu> getMenusWithChild();
}
