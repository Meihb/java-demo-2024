package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 指定增强
//@ControllerAdvice(assignableTypes = {S001LoginController.class, S002LogoutController.class})
//@ControllerAdvice(basePackages = "com.example.demo")
public class S_ControllerAdvice {

    @Autowired
    private HttpServletRequest request;

    // 方式1: @ModelAttribute注解的使用
    @ModelAttribute
    public void initData1(Model model) {
        request.setAttribute("num1", 66);
        model.addAttribute("userMail", List.of("123@mail.com", "456@mail.com"));

    }

    // 方式2: @ModelAttribute注解的使用
    @ModelAttribute(name = "userInfo")
    public Map<String, String> initData2() {
        request.setAttribute("num2", 99);
        return new HashMap<>(){
            {
                put("name", "贾飞天");
                put("age", "18");
            }
        };
    }

    // 捕获S001LoginController或S002LogoutController类中的Exception异常
    @ExceptionHandler(Exception.class)
    public void exceptionHandle(Exception ex) throws Exception {

        System.out.println();
        System.out.println("here is the exception handler");
        System.out.println(ex.getMessage());
//        throw ex;
    }
}

