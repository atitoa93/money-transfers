package dev.atito;

import dev.atito.command.CommandRouter;
import dev.atito.command.RequestCommandController;
import dev.atito.event.bus.EventBus;
import dev.atito.event.bus.GuavaEventBus;
import dev.atito.event.store.EventStore;
import dev.atito.event.store.InMemoryEventStore;
import dev.atito.event.handler.EventHandler;
import io.javalin.Javalin;
import dev.atito.query.*;

public class Main {
    public static void main(String[] arg) {
        // Init HTTP app
        Javalin app = HttpApp.getApp();

        // Init event bus with Guava dev.atito.event bus implementation
        EventBus eventBus = new GuavaEventBus();

        // Init event store with in-memory store
        EventStore eventStore = new InMemoryEventStore();
        eventBus.register(eventStore);

        // Init request handler
        EventHandler eventHandler = new EventHandler(eventBus);
        eventBus.register(eventHandler);

        // Init query side
        AccountQueryController.init(eventBus);
        RequestQueryController.init(eventBus);
        TransferQueryController.init(eventBus);
        HistoryQueryController.init(eventStore); // `/history` endpoint for auditing purposes
        QueryRouter.init(app);

        // Init command side
        RequestCommandController.init(eventBus);
        CommandRouter.init(app);
    }
}
