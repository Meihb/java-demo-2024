package com.example.demo.controller;

import com.example.demo.entity.Menu;
import com.example.demo.services.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MenuService MenuService;

    @GetMapping("/test_menu")
    @ResponseBody
    public String getMenu() {
        return "menu";
    }

    @GetMapping("/test_menu_sql")
    @ResponseBody
    public Menu getMenuSql(HttpServletRequest request) {
        return MenuService.getMenuByColumn("name", "菜单");
    }
}
