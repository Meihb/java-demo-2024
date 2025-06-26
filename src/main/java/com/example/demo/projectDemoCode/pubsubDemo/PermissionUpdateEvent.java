package com.example.demo.projectDemoCode.pubsubDemo;

public class PermissionUpdateEvent {
    private final Long roleId;

    public PermissionUpdateEvent(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
