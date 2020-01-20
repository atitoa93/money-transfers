package dev.atito.event.handler;

import dev.atito.domain.account.Account;
import dev.atito.domain.request.Request;
import dev.atito.domain.request.body.DepositAccountRequestBody;
import dev.atito.domain.request.status.RequestStatus;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountUpdatedEvent;
import dev.atito.projection.AccountProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

public class DepositAccountRequestHandlerTest {
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
    public void shouldDepositAccountSuccessfully() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DepositAccountRequestBody(accountId, BigDecimal.ONE));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(new Account(accountId, BigDecimal.ZERO));

        DepositAccountRequestHandler.handle(request, eventBus, accountProjection);

        Mockito.verify(eventBus).postEvent(Mockito.any(AccountUpdatedEvent.class));
    }

    @Test
    public void shouldThrowExceptionIfAmountLessThanOREqualZero() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DepositAccountRequestBody(accountId, BigDecimal.ZERO));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(new Account(accountId, BigDecimal.ZERO));

        Assertions.assertThrows(Exception.class, () -> DepositAccountRequestHandler.handle(request, eventBus, accountProjection));
    }

    @Test
    public void shouldThrowExceptionIfAccountDoesNotExist() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DepositAccountRequestBody(accountId, BigDecimal.ONE));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> DepositAccountRequestHandler.handle(request, eventBus, accountProjection));
    }

    @Test
    public void shouldUpdateRequestWithUpdatedAccount() throws Exception {
        UUID accountId = UUID.randomUUID();
        Mockito.when(request.getRequestBody()).thenReturn(new DepositAccountRequestBody(accountId, new BigDecimal(100)));
        Mockito.when(accountProjection.getOne(accountId)).thenReturn(new Account(accountId, BigDecimal.ONE));

        DepositAccountRequestHandler.handle(request, eventBus, accountProjection);

        ArgumentCaptor<RequestStatus> argumentCaptor = ArgumentCaptor.forClass(RequestStatus.class);
        Mockito.verify(request).setRequestStatus(argumentCaptor.capture());
        SuccessRequestStatus successRequestStatus = (SuccessRequestStatus) argumentCaptor.getValue();
        Account account = (Account) successRequestStatus.getResource();
        Assertions.assertEquals(new BigDecimal(101), account.getBalance());
    }
}
