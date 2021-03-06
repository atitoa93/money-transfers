package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.WithdrawAccountRequestBody;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountUpdatedEvent;
import dev.atito.event.pool.RequestUpdatedEvent;
import dev.atito.projection.AccountProjection;

import java.math.BigDecimal;

public class WithdrawAccountRequestHandler {
    public static void handle(Request request, EventBus eventBus, AccountProjection accountProjection) throws Exception {
        WithdrawAccountRequestBody requestBody = (WithdrawAccountRequestBody) request.getRequestBody();

        // Request should fail if the withdraw amount less than or equal zero
        if (requestBody.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Withdraw amount should be more than zero");
        }

        Account account = accountProjection.getOne(requestBody.getAccountId());

        // Request should fail if account doesn't exist
        if (account == null) {
            throw new Exception("Account doesn't exist");
        }

        // Make sure the account has enough fund
        if (account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new Exception("Not sufficient funds");
        }

        // Perform the withdraw operation
        account.setBalance(account.getBalance().subtract(requestBody.getAmount()));

        AccountUpdatedEvent accountUpdatedEvent = new AccountUpdatedEvent(account);
        eventBus.postEvent(accountUpdatedEvent);

        request.setRequestStatus(new SuccessRequestStatus(account));
        RequestUpdatedEvent requestUpdatedEvent = new RequestUpdatedEvent(request);
        eventBus.postEvent(requestUpdatedEvent);
    }
}
