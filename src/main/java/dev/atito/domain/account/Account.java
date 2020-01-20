package dev.atito.domain.account;

import dev.atito.domain.Resource;

import java.math.BigDecimal;
import java.util.UUID;

public class Account implements Resource {
    private UUID id;
    private BigDecimal balance;

    public Account(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public Account clone() {
        return new Account(id, balance);
    }
}
