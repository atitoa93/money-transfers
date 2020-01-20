package dev.atito.event.store;

import dev.atito.event.Event;

public interface EventStore {
    Event[] getAll();
    void append(Event event);
}
