package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.DeleteAccountRequestBody;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountDeletedEvent;
import dev.atito.projection.AccountProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

public class DeleteAccountRequestHandlerTest {
    Request request;
    EventBus eventBus;
    AccountProjection accountProjection;

    @BeforeEach
    public void init() {
        request = Mockito.mock(Request.class);
        eventBus = Mockito.mock(EventBus.class);
        accountProjection = Mockito.mock(AccountProjection.class);
    }

    @Test
    public void shouldDeleteAccountSuccessfully() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DeleteAccountRequestBody(accountId));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(new Account(accountId, BigDecimal.ZERO));

        DeleteAccountRequestHandler.handle(request, eventBus, accountProjection);

        Mockito.verify(eventBus).postEvent(Mockito.any(AccountDeletedEvent.class));
    }

    @Test
    public void shouldThrowExceptionIfAccountBalanceNotZero() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DeleteAccountRequestBody(accountId));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(new Account(accountId, BigDecimal.ONE));

        Assertions.assertThrows(Exception.class, () -> DeleteAccountRequestHandler.handle(request, eventBus, accountProjection));
    }

    @Test
    public void shouldThrowExceptionIfAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DeleteAccountRequestBody(accountId));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> DeleteAccountRequestHandler.handle(request, eventBus, accountProjection));
    }
}
