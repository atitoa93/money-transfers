package dev.atito.event.pool;

import dev.atito.domain.transfer.Transfer;
import dev.atito.event.Event;

public class TransferCreatedEvent extends Event {
    private final Transfer transfer;

    public TransferCreatedEvent(Transfer transfer) {
        super("Transfer Created Event");

        this.transfer = transfer.clone();
    }

    public Transfer getTransfer() {
        return transfer.clone();
    }
}
