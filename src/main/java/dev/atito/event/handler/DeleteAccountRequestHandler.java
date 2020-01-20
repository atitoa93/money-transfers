package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.DeleteAccountRequestBody;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountDeletedEvent;
import dev.atito.event.pool.RequestUpdatedEvent;
import dev.atito.projection.AccountProjection;

import java.math.BigDecimal;

public class DeleteAccountRequestHandler {
    public static void handle(Request request, EventBus eventBus, AccountProjection accountProjection) throws Exception {
        DeleteAccountRequestBody requestBody = (DeleteAccountRequestBody) request.getRequestBody();

        Account account = accountProjection.getOne(requestBody.getAccountId());

        // Request should fail if the request account to be deleted doesn't exist
        if (account == null) {
            throw new Exception("Account doesn't exist");
        }

        // Account balance must be zero to be deleted
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("Account balance must be zero to be deleted");
        }

        AccountDeletedEvent accountDeletedEvent = new AccountDeletedEvent(account);
        eventBus.postEvent(accountDeletedEvent);

        request.setRequestStatus(new SuccessRequestStatus(account));
        RequestUpdatedEvent requestUpdatedEvent = new RequestUpdatedEvent(request);
        eventBus.postEvent(requestUpdatedEvent);
    }
}
