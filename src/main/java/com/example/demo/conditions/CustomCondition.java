package com.example.demo.conditions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public class CustomCondition implements Condition {

    /**
     * Determine if the condition matches.
     * @param context the condition context
     * @param metadata the metadata of the {@link org.springframework.core.type.AnnotationMetadata class}
     * or {@link org.springframework.core.type.MethodMetadata method} being checked
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 自定义逻辑，例如检查当前节点的某种状态
        // 可以使用 context.getEnvironment() 来访问环境变量、配置等
//        String nodeName = context.getEnvironment().getProperty("node.name");
//        System.out.println("get nodeName: " + nodeName);
        // 根据节点名称或其他属性决定是否满足条件
//        return "desiredNode".equals(nodeName);
        log.info("Evaluating custom condition for metadata: " + metadata);

        try {

            // 获取本地主机名
            String hostname = InetAddress.getLocalHost().getHostName();
            System.out.println("Hostname: " + hostname);
            return hostname.contains("H1G");
        } catch (IOException e) {
            return false;
        }
    }
}
