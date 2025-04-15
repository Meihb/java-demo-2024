package com.example.demo.accountScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountScopeConfigurer implements BeanFactoryPostProcessor {

    @Autowired
    private AccountScopeContext context;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory instanceof ConfigurableBeanFactory) {
            ((ConfigurableBeanFactory) beanFactory)
                    .registerScope("account", new AccountScope(context));
        }
    }
}
