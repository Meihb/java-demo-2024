package com.example.demo.accountScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

    @Autowired
    private ConfigurableApplicationContext context;

    public Account getAccount(Long accountId) {
        String beanName = "account#" + accountId;

        if (!context.containsBean(beanName)) {
            context.getBeanFactory().registerSingleton(beanName, new Account(accountId));
        }

        return (Account) context.getBean(beanName);
    }
}
