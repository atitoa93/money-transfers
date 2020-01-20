package dev.atito.query;

import dev.atito.event.Event;
import dev.atito.event.store.EventStore;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

public class HistoryQueryController {
    private static EventStore eventStore;

    public static void init(EventStore eventStore) {
        HistoryQueryController.eventStore = eventStore;
    }

    @OpenApi(
            path = "/history",
            method = HttpMethod.GET,
            tags = {"History"},
            summary = "Get history of all events",
            operationId = "getAllHistory",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Event[].class)})
            }
    )
    public static void getAll(Context ctx) {
        ctx.status(200).json(eventStore.getAll());
    }
}
