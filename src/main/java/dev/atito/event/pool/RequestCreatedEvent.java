package dev.atito.event.pool;

import dev.atito.domain.request.Request;
import dev.atito.event.Event;

public final class RequestCreatedEvent extends Event {
    private final Request request;

    public RequestCreatedEvent(Request request) {
        super("Request Created Event");

        this.request = request.clone();
    }

    public Request getRequest() {
        return request.clone();
    }
}
