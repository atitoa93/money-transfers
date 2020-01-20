package dev.atito.event.bus;

import com.google.common.eventbus.AsyncEventBus;
import dev.atito.event.Event;

import java.util.concurrent.Executors;

public class GuavaEventBus implements EventBus {
    com.google.common.eventbus.EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());

    @Override
    public void register(Object object) {
        eventBus.register(object);
    }

    @Override
    public void postEvent(Event event) {
        eventBus.post(event);
    }
}
