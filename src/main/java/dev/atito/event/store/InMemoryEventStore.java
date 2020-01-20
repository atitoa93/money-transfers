package dev.atito.event.store;

import com.google.common.eventbus.Subscribe;
import dev.atito.event.Event;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryEventStore implements EventStore {
    private ConcurrentLinkedQueue<Event> events;

    public InMemoryEventStore() {
        events = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Event[] getAll() {
        return events.toArray(new Event[0]);
    }

    @Override
    @Subscribe
    public void append(Event event) {
        events.add(event);
    }
}
