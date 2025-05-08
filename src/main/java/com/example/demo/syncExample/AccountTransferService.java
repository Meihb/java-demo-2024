package com.example.demo.syncExample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountTransferService {

    private final AllocatorTest2 allocator;

    public void transfer(Account from, Account to, BigDecimal amt) {
        // 分配资源锁
        allocator.apply(from.getId(), to.getId());
        try {
            // 确保锁定顺序一致
            Account first = from.getId().compareTo(to.getId()) < 0 ? from : to;
            Account second = from.getId().compareTo(to.getId()) < 0 ? to : from;

            synchronized (first) {
                synchronized (second) {
                    if (from.getBalance().compareTo(amt) < 0) {
                        throw new RuntimeException("余额不足");
                    }
                    from.setBalance(from.getBalance().subtract(amt));
                    to.setBalance(to.getBalance().add(amt));
                }
            }
        } finally {
            allocator.free(from.getId(), to.getId());
        }
    }
}
