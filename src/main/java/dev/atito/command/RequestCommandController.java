package dev.atito.command;

import dev.atito.event.bus.EventBus;
import dev.atito.event.pool.RequestCreatedEvent;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import dev.atito.domain.request.*;
import dev.atito.domain.request.body.*;
import dev.atito.domain.request.status.PendingRequestStatus;
import dev.atito.domain.request.status.RequestStatus;

import java.util.UUID;

public class RequestCommandController {
    private static EventBus eventBus;

    public static void init(EventBus eventBus) {
        RequestCommandController.eventBus = eventBus;
    }

    @OpenApi(
            path = "/requests",
            method = HttpMethod.POST,
            tags = {"Request"},
            summary = "Create request",
            operationId = "createRequest",
            composedRequestBody = @OpenApiComposedRequestBody(
                    oneOf = {
                            @OpenApiContent(from = CreateAccountRequestBody.class),
                            @OpenApiContent(from = DeleteAccountRequestBody.class),
                            @OpenApiContent(from = TransferRequestBody.class),
                    },
                    required = true,
                    contentType = "application/json"
            ),
            responses = {
                    @OpenApiResponse(status = "201", content = {@OpenApiContent(from = Request.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = NotFoundResponse.class)})
            }
    )
    public static void create(Context ctx) {
        UUID id = UUID.randomUUID();
        RequestBody requestBody;
        try {
            requestBody = RequestBodyFactory.getRequestBody(ctx.body());
        } catch (Exception e) {
            throw new BadRequestResponse(e.getMessage());
        }
        RequestStatus requestStatus = new PendingRequestStatus();
        Request request = new Request(id, requestBody, requestStatus);

        RequestCreatedEvent event = new RequestCreatedEvent(request);
        eventBus.postEvent(event);
        ctx.status(201).json(request);
    }
}
