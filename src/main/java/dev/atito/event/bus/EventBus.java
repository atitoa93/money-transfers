package dev.atito.event.bus;

import dev.atito.event.Event;

public interface EventBus {
    void register(Object object);
    void postEvent(Event event);
}
