package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.util.List;

public class Menu {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String icon;
    private Integer permission_id;
    private Integer parent_id;
    private String route;
    private Integer sort;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<Menu> childMenu;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getParentId() {
        return parent_id;
    }

    public void setParentId(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    public Integer getPermissionId() {
        return permission_id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setPermission_id(Integer permission_id) {
        this.permission_id = permission_id;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public LocalDateTime getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(LocalDateTime created_time) {
//        this.createdTime = created_time;
//    }
//
//    public LocalDateTime getUpdatedTime() {
//        return updatedTime;
//    }
//
//    public void setUpdatedTime(LocalDateTime updatedTime) {
//        this.updatedTime = updatedTime;
//    }

}
