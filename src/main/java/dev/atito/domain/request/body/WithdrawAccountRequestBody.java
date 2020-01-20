package dev.atito.domain.request.body;

import dev.atito.domain.request.RequestType;

import java.math.BigDecimal;
import java.util.UUID;

public class WithdrawAccountRequestBody extends RequestBody {
    private UUID accountId;
    private BigDecimal amount;

    public WithdrawAccountRequestBody(UUID accountId, BigDecimal amount) {
        super(RequestType.WITHDRAW_ACCOUNT);

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
    public WithdrawAccountRequestBody clone() {
        return new WithdrawAccountRequestBody(accountId, amount);
    }
}
