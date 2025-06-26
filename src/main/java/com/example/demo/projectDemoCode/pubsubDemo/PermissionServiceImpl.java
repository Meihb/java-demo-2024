package com.example.demo.projectDemoCode.pubsubDemo;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final Map<Long, List<String>> adminPermissions = new HashMap<>();
    private final Map<Long, List<Long>> roleAdmins = new HashMap<>();

    public PermissionServiceImpl() {
        adminPermissions.put(1L, new ArrayList<>(Arrays.asList("READ", "WRITE")));
        adminPermissions.put(2L, new ArrayList<>(Collections.singletonList("READ")));
        adminPermissions.put(3L, new ArrayList<>(Collections.singletonList("EXECUTE")));

        roleAdmins.put(100L, new ArrayList<>(Arrays.asList(1L, 2L)));
        roleAdmins.put(200L, new ArrayList<>(Collections.singletonList(3L)));
    }

    @Override
    public List<String> queryPermissionsByAdminId(Long adminId) {
        return adminPermissions.getOrDefault(adminId, Collections.emptyList());
    }

    @Override
    public List<Long> findAdminIdsByRole(Long roleId) {
        return roleAdmins.getOrDefault(roleId, Collections.emptyList());
    }

    @Override
    public void updatePermissionsForRole(Long roleId, List<String> newPermissions) {
        List<Long> admins = roleAdmins.get(roleId);
        if (admins != null) {
            for (Long adminId : admins) {
                adminPermissions.put(adminId, new ArrayList<>(newPermissions));
            }
        }
    }
}
