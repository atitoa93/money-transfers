package dev.atito.repository.transfer;

import dev.atito.domain.transfer.Transfer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTransferRepository implements TransferRepository {
    ConcurrentHashMap<UUID, Transfer> db = new ConcurrentHashMap<>();

    @Override
    public Transfer[] getAll() {
        return db.values().toArray(new Transfer[0]);
    }

    @Override
    public Transfer getOne(UUID id) {
        return db.get(id);
    }

    @Override
    public Transfer create(Transfer transfer) {
        return db.put(transfer.getId(), transfer);
    }

    @Override
    public Transfer update(Transfer transfer) {
        return db.computeIfPresent(transfer.getId(), (key, oldTransfer) -> transfer);
    }

    @Override
    public Transfer delete(UUID id) {
        return db.remove(id);
    }
}
