package com.example.demo.accountScope;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountFactory accountFactory;

    @GetMapping("/account/{id}")
    public String test(@PathVariable Long id) {
        Account account = accountFactory.getAccount(id);
        return account.toString();
    }
}
