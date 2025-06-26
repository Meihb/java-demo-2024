package com.example.demo.projectDemoCode.pubsubDemo;

import com.google.common.eventbus.EventBus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionService permissionService;
    private final EventBus eventBus;
    private final RedisTemplate<String, String> redisTemplate;
    private final ChannelTopic permissionTopic;

    public PermissionController(PermissionService permissionService, EventBus eventBus,
                                RedisTemplate<String, String> redisTemplate, ChannelTopic permissionTopic) {
        this.permissionService = permissionService;
        this.eventBus = eventBus;
        this.redisTemplate = redisTemplate;
        this.permissionTopic = permissionTopic;
    }

    // 查询管理员权限缓存（走PermissionCacheService效果类似）
    @GetMapping("/admin/{adminId}")
    public List<String> getPermissions(@PathVariable Long adminId) {
        return permissionService.queryPermissionsByAdminId(adminId);
    }

    // 更新角色权限（模拟），并发布更新事件到Guava EventBus和Redis
    @PostMapping("/role/{roleId}/update")
    public String updateRolePermissions(@PathVariable Long roleId, @RequestBody List<String> newPermissions) {
        // 更新权限数据
        permissionService.updatePermissionsForRole(roleId, newPermissions);

        // 发布本地EventBus事件，刷新本实例缓存
        eventBus.post(new PermissionUpdateEvent(roleId));

        // 发布Redis消息，通知集群其他实例刷新缓存
        redisTemplate.convertAndSend(permissionTopic.getTopic(), roleId.toString());

        return "角色权限更新成功，roleId=" + roleId;
    }
}
