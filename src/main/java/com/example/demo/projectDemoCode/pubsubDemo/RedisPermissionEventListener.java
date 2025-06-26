package com.example.demo.projectDemoCode.pubsubDemo;

import com.google.common.eventbus.EventBus;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisPermissionEventListener implements MessageListener {

    private final EventBus eventBus;

    public RedisPermissionEventListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String roleIdStr = message.toString();
        Long roleId;
        try {
            roleId = Long.parseLong(roleIdStr);
            System.out.println("[Redis订阅] 收到权限更新通知，roleId=" + roleId);
            // 转发事件到本地Guava EventBus
            eventBus.post(new PermissionUpdateEvent(roleId));
        } catch (NumberFormatException e) {
            System.err.println("无效的roleId消息：" + roleIdStr);
        }
    }
}
