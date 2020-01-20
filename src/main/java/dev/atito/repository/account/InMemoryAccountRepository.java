package dev.atito.repository.account;

import dev.atito.domain.account.Account;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAccountRepository implements AccountRepository {
    ConcurrentHashMap<UUID, Account> db = new ConcurrentHashMap<>();

    @Override
    public Account[] getAll() {
        return db.values().toArray(new Account[0]);
    }

    @Override
    public Account getOne(UUID id) {
        return db.get(id);
    }

    @Override
    public Account create(Account account) {
        return db.put(account.getId(), account);
    }

    @Override
    public Account update(Account account) {
        return db.computeIfPresent(account.getId(), (key, oldAccount) -> account);
    }

    @Override
    public Account delete(UUID id) {
        return db.remove(id);
    }
}
