package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountCreatedEvent;
import dev.atito.event.pool.RequestUpdatedEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountRequestHandler {
    public static void handle(Request request, EventBus eventBus) {
        // New accounts start with zero balance
        Account account = new Account(UUID.randomUUID(), BigDecimal.ZERO);

        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(account);
        eventBus.postEvent(accountCreatedEvent);

        request.setRequestStatus(new SuccessRequestStatus(account));
        RequestUpdatedEvent requestUpdatedEvent = new RequestUpdatedEvent(request);
        eventBus.postEvent(requestUpdatedEvent);
    }
}
