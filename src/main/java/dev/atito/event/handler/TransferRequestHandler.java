package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.TransferRequestBody;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.domain.transfer.Transfer;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountUpdatedEvent;
import dev.atito.event.pool.RequestUpdatedEvent;
import dev.atito.event.pool.TransferCreatedEvent;
import dev.atito.projection.AccountProjection;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequestHandler {
    public static void handle(Request request, EventBus eventBus, AccountProjection accountProjection) throws Exception {
        TransferRequestBody requestBody = (TransferRequestBody) request.getRequestBody();

        // Request should fail if the transfer amount less than or equal zero
        if (requestBody.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Transfer amount should be more than zero");
        }

        Account fromAccount = accountProjection.getOne(requestBody.getFromAccountId());
        Account toAccount = accountProjection.getOne(requestBody.getToAccountId());

        // Request should fail if one of the accounts doesn't exist
        if (fromAccount == null || toAccount == null) {
            throw new Exception("Account doesn't exist");
        }

        // Make sure the from account has enough funds
        if (fromAccount.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new Exception("Not sufficient funds");
        }

        // Perform the transfer operation
        fromAccount.setBalance(fromAccount.getBalance().subtract(requestBody.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(requestBody.getAmount()));

        AccountUpdatedEvent fromAccountUpdatedEvent = new AccountUpdatedEvent(fromAccount);
        eventBus.postEvent(fromAccountUpdatedEvent);

        AccountUpdatedEvent toAccountUpdatedEvent = new AccountUpdatedEvent(toAccount);
        eventBus.postEvent(toAccountUpdatedEvent);

        Transfer transfer = new Transfer(
                UUID.randomUUID(),
                requestBody.getFromAccountId(),
                requestBody.getToAccountId(),
                requestBody.getAmount()
        );
        TransferCreatedEvent transferCreatedEvent = new TransferCreatedEvent(transfer);
        eventBus.postEvent(transferCreatedEvent);

        request.setRequestStatus(new SuccessRequestStatus(transfer));
        RequestUpdatedEvent requestUpdatedEvent = new RequestUpdatedEvent(request);
        eventBus.postEvent(requestUpdatedEvent);
    }
}
