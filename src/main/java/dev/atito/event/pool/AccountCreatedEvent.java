package dev.atito.event.pool;

import dev.atito.domain.account.Account;
import dev.atito.event.Event;

public final class AccountCreatedEvent extends Event {
    private final Account account;

    public AccountCreatedEvent(Account account) {
        super("Account Created Event");

        this.account = account.clone();
    }

    public Account getAccount() {
        return account.clone();
    }
}
