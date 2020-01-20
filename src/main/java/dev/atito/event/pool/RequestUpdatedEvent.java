package dev.atito.event.pool;

import dev.atito.domain.request.Request;
import dev.atito.event.Event;

public class RequestUpdatedEvent extends Event {
    private final Request request;

    public RequestUpdatedEvent(Request request) {
        super("Request Updated Event");

        this.request = request.clone();
    }

    public Request getRequest() {
        return request.clone();
    }
}
