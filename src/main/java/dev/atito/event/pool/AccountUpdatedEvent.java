package dev.atito.event.pool;

import dev.atito.domain.account.Account;
import dev.atito.event.Event;

public class AccountUpdatedEvent extends Event {
    private final Account account;

    public AccountUpdatedEvent(Account account) {
        super("Account Updated Event");

        this.account = account.clone();
    }

    public Account getAccount() {
        return account.clone();
    }
}
