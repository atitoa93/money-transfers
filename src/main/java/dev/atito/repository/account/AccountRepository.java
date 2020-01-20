package dev.atito.repository.account;

import dev.atito.domain.account.Account;

import java.util.UUID;

public interface AccountRepository {
    Account[] getAll();
    Account getOne(UUID id);
    Account create(Account account);
    Account update(Account account);
    Account delete(UUID id);
}
