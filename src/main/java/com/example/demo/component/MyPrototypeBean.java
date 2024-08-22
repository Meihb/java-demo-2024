package com.example.demo.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // 标识为原型作用域
public class MyPrototypeBean {

    private String value;

    public MyPrototypeBean() {
        System.out.println("in MyPrototypeBean construction");
        this.value = "Initial Value";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
