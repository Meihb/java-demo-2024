package com.example.demo.accountScope;

public class Account {
    private final Long id;

    public Account(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String toString() {
        return "Account{id=" + id + '}';
    }
}
