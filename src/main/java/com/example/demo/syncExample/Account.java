package com.example.demo.syncExample;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private final String id;
    private BigDecimal balance;

    public Account(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
}
