package com.example.demo.component;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class Mail implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private String recipient;//邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容
}
