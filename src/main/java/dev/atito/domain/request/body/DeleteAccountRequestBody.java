package dev.atito.domain.request.body;

import dev.atito.domain.request.RequestType;

import java.util.UUID;

public class DeleteAccountRequestBody extends RequestBody {
    public UUID accountId;

    public DeleteAccountRequestBody(UUID accountId) {
        super(RequestType.DELETE_ACCOUNT);

        this.accountId = accountId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @Override
    public DeleteAccountRequestBody clone() {
        return new DeleteAccountRequestBody(accountId);
    }
}
