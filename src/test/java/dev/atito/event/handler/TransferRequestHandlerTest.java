package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.TransferRequestBody;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountUpdatedEvent;
import dev.atito.projection.AccountProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferRequestHandlerTest {
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
    public void shouldTransferSuccessfully() throws Exception {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(100);

        Mockito.when(request.getRequestBody()).thenReturn(new TransferRequestBody(fromAccountId, toAccountId, amount));
        Mockito.when(accountProjection.getOne(fromAccountId)).thenReturn(new Account(fromAccountId, new BigDecimal(200)));
        Mockito.when(accountProjection.getOne(toAccountId)).thenReturn(new Account(toAccountId, new BigDecimal(50)));

        TransferRequestHandler.handle(request, eventBus, accountProjection);

        Mockito.verify(eventBus, Mockito.times(2)).postEvent(Mockito.any(AccountUpdatedEvent.class));
    }

    @Test
    public void shouldThrowExceptionIfAmountLessThanOREqualZero() throws Exception {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.ZERO;

        Mockito.when(request.getRequestBody()).thenReturn(new TransferRequestBody(fromAccountId, toAccountId, amount));
        Mockito.when(accountProjection.getOne(fromAccountId)).thenReturn(new Account(fromAccountId, new BigDecimal(200)));
        Mockito.when(accountProjection.getOne(toAccountId)).thenReturn(new Account(toAccountId, new BigDecimal(50)));

        Assertions.assertThrows(Exception.class, () -> TransferRequestHandler.handle(request, eventBus, accountProjection));
    }

    @Test
    public void shouldThrowExceptionIfAccountDoesNotExist() throws Exception {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.ZERO;

        Mockito.when(request.getRequestBody()).thenReturn(new TransferRequestBody(fromAccountId, toAccountId, amount));
        Mockito.when(accountProjection.getOne(fromAccountId)).thenReturn(null);
        Mockito.when(accountProjection.getOne(toAccountId)).thenReturn(new Account(toAccountId, new BigDecimal(50)));

        Assertions.assertThrows(Exception.class, () -> TransferRequestHandler.handle(request, eventBus, accountProjection));
    }

    @Test
    public void shouldThrowExceptionIfNotSufficientFunds() throws Exception {
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal(200);

        Mockito.when(request.getRequestBody()).thenReturn(new TransferRequestBody(fromAccountId, toAccountId, amount));
        Mockito.when(accountProjection.getOne(fromAccountId)).thenReturn(new Account(toAccountId, new BigDecimal(150)));
        Mockito.when(accountProjection.getOne(toAccountId)).thenReturn(new Account(toAccountId, new BigDecimal(50)));

        Assertions.assertThrows(Exception.class, () -> TransferRequestHandler.handle(request, eventBus, accountProjection));
    }
}
