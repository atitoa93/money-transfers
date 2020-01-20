package dev.atito.projection;

import com.google.common.eventbus.Subscribe;
import dev.atito.event.pool.AccountCreatedEvent;
import dev.atito.event.pool.AccountDeletedEvent;
import dev.atito.event.pool.AccountUpdatedEvent;
import dev.atito.domain.account.Account;
import dev.atito.repository.account.AccountRepository;
import dev.atito.repository.account.InMemoryAccountRepository;

import java.util.UUID;

public class AccountProjection {
    private AccountRepository accountRepository = new InMemoryAccountRepository();

    public Account[] getAll() {
        return accountRepository.getAll();
    }

    public Account getOne(UUID id) {
        return accountRepository.getOne(id);
    }

    @Subscribe
    public void create(AccountCreatedEvent event) {
        accountRepository.create(event.getAccount());
    }

    @Subscribe
    public void update(AccountUpdatedEvent event) {
        accountRepository.update(event.getAccount());
    }

    @Subscribe
    public void delete(AccountDeletedEvent event) {
        accountRepository.delete(event.getAccount().getId());
    }
}
