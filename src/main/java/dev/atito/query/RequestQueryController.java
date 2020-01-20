package dev.atito.query;

import dev.atito.domain.request.Request;
import dev.atito.event.bus.EventBus;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import dev.atito.projection.RequestProjection;

import java.util.UUID;

public class RequestQueryController {
    private static RequestProjection requestProjection = new RequestProjection();

    public static void init(EventBus eventBus) {
        eventBus.register(requestProjection);
    }

    @OpenApi(
            path = "/requests",
            method = HttpMethod.GET,
            tags = {"Request"},
            summary = "Get all requests",
            operationId = "getAllRequests",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Request[].class)})
            }
    )
    public static void getAll(Context ctx) {
        ctx.status(200).json(requestProjection.getAll());
    }

    @OpenApi(
            path = "/requests/:id",
            method = HttpMethod.GET,
            tags = {"Request"},
            summary = "Get request by id",
            operationId = "getRequestById",
            pathParams = @OpenApiParam(name = "id", description = "Request id"),
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Request.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = NotFoundResponse.class)})
            }
    )
    public static void getOne(Context ctx) {
        UUID requestId = UUID.fromString(ctx.pathParam("id"));
        Request request = requestProjection.getOne(requestId);
        if (request == null) {
            throw new NotFoundResponse("Request doesn't exist");
        }
        ctx.status(200).json(request);
    }
}
