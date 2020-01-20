package dev.atito.projection;

import com.google.common.eventbus.Subscribe;
import dev.atito.event.pool.TransferCreatedEvent;
import dev.atito.domain.transfer.Transfer;
import dev.atito.repository.transfer.TransferRepository;
import dev.atito.repository.transfer.InMemoryTransferRepository;

import java.util.UUID;

public class TransferProjection {
    private TransferRepository transferRepository = new InMemoryTransferRepository();

    public Transfer[] getAll() {
        return transferRepository.getAll();
    }

    public Transfer getOne(UUID id) {
        return transferRepository.getOne(id);
    }

    @Subscribe
    public void create(TransferCreatedEvent event) {
        transferRepository.create(event.getTransfer());
    }
}
