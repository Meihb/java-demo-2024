package com.example.demo.services;

import com.example.demo.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService {
    Menu getMenuByColumn(String column,String value);
    List<Menu> getMenusWithChild();
}
