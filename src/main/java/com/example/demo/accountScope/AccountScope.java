package com.example.demo.accountScope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class AccountScope implements Scope {

    private final AccountScopeContext context;

    public AccountScope(AccountScopeContext context) {
        this.context = context;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object bean = context.get(name);
        if (bean == null) {
            bean = objectFactory.getObject();
            context.put(name, bean);
        }
        return bean;
    }

    @Override
    public Object remove(String name) {
        return context.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // 可选实现销毁处理
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "account";
    }
}
