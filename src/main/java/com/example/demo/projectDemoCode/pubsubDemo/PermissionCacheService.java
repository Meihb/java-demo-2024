package com.example.demo.projectDemoCode.pubsubDemo;

import com.google.common.cache.Cache;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionCacheService {

    private final PermissionService permissionService;
    private final Cache<Long, List<String>> permissionCache;

    public PermissionCacheService(PermissionService permissionService, Cache<Long, List<String>> permissionCache) {
        this.permissionService = permissionService;
        this.permissionCache = permissionCache;
    }

    // 缓存查询，自动加载权限
    public List<String> getPermissions(Long adminId) {
        List<String> cached = permissionCache.getIfPresent(adminId);
        if (cached != null) {
            return cached;
        }
        List<String> perms = permissionService.queryPermissionsByAdminId(adminId);
        permissionCache.put(adminId, perms);
        return perms;
    }

    // 监听本地权限更新事件，刷新缓存
    @Subscribe
    public void handlePermissionUpdate(PermissionUpdateEvent event) {
        System.out.println("[EventBus] 收到权限更新事件，roleId=" + event.getRoleId());
        List<Long> adminIds = permissionService.findAdminIdsByRole(event.getRoleId());
        for (Long adminId : adminIds) {
            List<String> perms = permissionService.queryPermissionsByAdminId(adminId);
            permissionCache.put(adminId, perms);
            System.out.println("刷新缓存 & 通知管理员 " + adminId + " 权限: " + perms);
        }
    }

    // 本地手动刷新缓存（比如通过接口调用）
    public void refreshCacheByRole(Long roleId) {
        handlePermissionUpdate(new PermissionUpdateEvent(roleId));
    }
}
