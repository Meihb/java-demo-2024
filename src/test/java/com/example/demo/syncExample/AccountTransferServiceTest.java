package com.example.demo.syncExample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTransferServiceTest {
    @Autowired
    private AccountTransferService transferService;
    @Test
    public void testSimpleTransfer() {
        Account a1 = new Account("A001", new BigDecimal("1000"));
        Account a2 = new Account("A002", new BigDecimal("300"));

        transferService.transfer(a1, a2, new BigDecimal("250"));

        assertEquals(new BigDecimal("750"), a1.getBalance());
        assertEquals(new BigDecimal("550"), a2.getBalance());
    }

    @Test
    public void testInsufficientBalance() {
        Account a1 = new Account("A001", new BigDecimal("100"));
        Account a2 = new Account("A002", new BigDecimal("300"));

        try {
            transferService.transfer(a1, a2, new BigDecimal("200"));
        } catch (RuntimeException e) {
            assertEquals("余额不足", e.getMessage());
        }

        assertEquals(new BigDecimal("100"), a1.getBalance());
        assertEquals(new BigDecimal("300"), a2.getBalance());
    }
}