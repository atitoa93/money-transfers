package dev.atito.event.handler;

import com.google.common.eventbus.Subscribe;
import dev.atito.domain.request.*;
import dev.atito.domain.request.status.FailureRequestStatus;
import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.*;
import dev.atito.projection.AccountProjection;

public class EventHandler {
    private EventBus eventBus;
    private AccountProjection accountProjection = new AccountProjection();

    public EventHandler(EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.register(accountProjection);
    }

    @Subscribe
    public void requestCreatedEventHandler(RequestCreatedEvent event) {
        Request request = event.getRequest();
        try {
            switch (request.getRequestBody().getRequestType()) {
                case CREATE_ACCOUNT: {
                    CreateAccountRequestHandler.handle(request, eventBus);
                    break;
                } case DEPOSIT_ACCOUNT: {
                    DepositAccountRequestHandler.handle(request, eventBus, accountProjection);
                    break;
                } case WITHDRAW_ACCOUNT: {
                    WithdrawAccountRequestHandler.handle(request, eventBus, accountProjection);
                    break;
                } case DELETE_ACCOUNT: {
                    DeleteAccountRequestHandler.handle(request, eventBus, accountProjection);
                    break;
                } case TRANSFER: {
                    TransferRequestHandler.handle(request, eventBus, accountProjection);
                    break;
                }
            }
        } catch (Exception e) {
            request.setRequestStatus(new FailureRequestStatus(e.getMessage()));
            RequestUpdatedEvent requestUpdatedEvent = new RequestUpdatedEvent(request);
            eventBus.postEvent(requestUpdatedEvent);
        }
    }
}
