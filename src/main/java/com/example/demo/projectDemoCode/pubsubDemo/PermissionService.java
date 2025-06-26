package com.example.demo.projectDemoCode.pubsubDemo;

import java.util.List;

public interface PermissionService {
    List<String> queryPermissionsByAdminId(Long adminId);
    List<Long> findAdminIdsByRole(Long roleId);
    void updatePermissionsForRole(Long roleId, List<String> newPermissions);
}
