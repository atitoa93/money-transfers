package dev.atito.event.pool;

import dev.atito.domain.account.Account;
import dev.atito.event.Event;

public class AccountDeletedEvent extends Event {
    private final Account account;

    public AccountDeletedEvent(Account account) {
        super("Account Deleted Event");

        this.account = account.clone();
    }

    public Account getAccount() {
        return account.clone();
    }
}
