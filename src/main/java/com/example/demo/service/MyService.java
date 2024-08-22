package com.example.demo.service;

import com.example.demo.component.MyPrototypeBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private MyPrototypeBean myPrototypeBean;

    @Autowired
    private ObjectFactory<MyPrototypeBean> myPrototypeBeanFactory;

    @Lookup
    public MyPrototypeBean getMyPrototypeBean() {
        return null; // Spring 会在运行时返回实际的 PrototypeBean 实例
    }

    public void doSomething() {
        myPrototypeBean.setValue("Changed Value");
        System.out.println("PrototypeBean value: " + myPrototypeBean.getValue());
    }

    public void doSomething2() {
        MyPrototypeBean myPrototypeBean = myPrototypeBeanFactory.getObject(); // 每次获取新的实例
        myPrototypeBean.setValue("Changed Value2");
        System.out.println("PrototypeBean value: " + myPrototypeBean.getValue());
    }

    public void doSomething3() {
        MyPrototypeBean myPrototypeBean = getMyPrototypeBean(); // 每次获取新的实例
        myPrototypeBean.setValue("Changed Value3");
        System.out.println("PrototypeBean value: " + myPrototypeBean.getValue());
    }
}
