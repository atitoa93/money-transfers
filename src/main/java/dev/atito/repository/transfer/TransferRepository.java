package dev.atito.repository.transfer;

import dev.atito.domain.transfer.Transfer;

import java.util.UUID;

public interface TransferRepository {
    Transfer[] getAll();
    Transfer getOne(UUID id);
    Transfer create(Transfer transfer);
    Transfer update(Transfer transfer);
    Transfer delete(UUID id);
}
