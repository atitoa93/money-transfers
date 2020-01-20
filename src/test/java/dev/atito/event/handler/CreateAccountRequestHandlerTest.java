package dev.atito.event.handler;

import dev.atito.domain.request.Request;
import dev.atito.domain.request.status.RequestStatus;
import dev.atito.domain.request.status.SuccessRequestStatus;
import dev.atito.event.Event;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.AccountCreatedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class CreateAccountRequestHandlerTest {
    Request request;
    EventBus eventBus;

    @BeforeEach
    public void init() {
        request = Mockito.mock(Request.class);
        eventBus = Mockito.mock(EventBus.class);
    }

    @Test
    public void shouldCreateAccountSuccessfully() {
        CreateAccountRequestHandler.handle(request, eventBus);

        Mockito.verify(eventBus, Mockito.times(1)).postEvent(Mockito.any(AccountCreatedEvent.class));
    }

    @Test
    public void shouldCreateAccountWithBalanceZero() {
        CreateAccountRequestHandler.handle(request, eventBus);

        ArgumentCaptor<Event> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        Mockito.verify(eventBus, Mockito.times(2)).postEvent(argumentCaptor.capture());
        AccountCreatedEvent accountCreatedEvent = (AccountCreatedEvent) argumentCaptor.getAllValues().get(0);
        Assertions.assertEquals(BigDecimal.ZERO, accountCreatedEvent.getAccount().getBalance());
    }

    @Test
    public void shouldUpdateRequestToSuccessStatus() {
        CreateAccountRequestHandler.handle(request, eventBus);

        ArgumentCaptor<RequestStatus> argumentCaptor = ArgumentCaptor.forClass(RequestStatus.class);
        Mockito.verify(request).setRequestStatus(argumentCaptor.capture());
        SuccessRequestStatus successRequestStatus = (SuccessRequestStatus) argumentCaptor.getValue();
        Assertions.assertEquals("Success", successRequestStatus.getStatus());
    }
}
