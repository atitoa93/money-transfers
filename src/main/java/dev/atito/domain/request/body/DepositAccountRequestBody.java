package dev.atito.domain.request.body;

import dev.atito.domain.request.RequestType;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositAccountRequestBody extends RequestBody {
    private UUID accountId;
    private BigDecimal amount;

    public DepositAccountRequestBody(UUID accountId, BigDecimal amount) {
        super(RequestType.DEPOSIT_ACCOUNT);

        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public DepositAccountRequestBody clone() {
        return new DepositAccountRequestBody(accountId, amount);
    }
}
