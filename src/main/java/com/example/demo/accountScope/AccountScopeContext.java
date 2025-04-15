package com.example.demo.accountScope;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class AccountScopeContext {
    private final ConcurrentMap<String, Object> accountBeans = new ConcurrentHashMap<>();

    public Object get(String name) {
        return accountBeans.get(name);
    }

    public void put(String name, Object bean) {
        accountBeans.put(name, bean);
    }

    public Object remove(String name) {
        return accountBeans.remove(name);
    }

    public void clear() {
        accountBeans.clear();
    }
}
