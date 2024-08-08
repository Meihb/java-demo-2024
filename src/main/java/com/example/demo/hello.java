package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller 表示其控制器身份
@ResponseBody 表示其会返回response

所以其实是 response+controller
 */
@RestController
public class hello {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello SpringBoot";
    }
}
