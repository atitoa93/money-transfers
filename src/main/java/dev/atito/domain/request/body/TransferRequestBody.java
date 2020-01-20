package dev.atito.domain.request.body;

import dev.atito.domain.request.RequestType;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequestBody extends RequestBody {
    public UUID fromAccountId;
    public UUID toAccountId;
    public BigDecimal amount;

    public TransferRequestBody(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
        super(RequestType.TRANSFER);

        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public UUID getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(UUID fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public UUID getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(UUID toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public TransferRequestBody clone() {
        return new TransferRequestBody(fromAccountId, toAccountId, amount);
    }
}
