package dev.atito.query;

import dev.atito.event.bus.EventBus;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import dev.atito.domain.transfer.Transfer;
import dev.atito.projection.TransferProjection;

import java.util.UUID;

public class TransferQueryController {
    private static TransferProjection transferProjection = new TransferProjection();

    public static void init(EventBus eventBus) {
        eventBus.register(transferProjection);
    }

    @OpenApi(
            path = "/transfers",
            method = HttpMethod.GET,
            tags = {"Transfer"},
            summary = "Get all transfers",
            operationId = "getAllTransfers",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Transfer[].class)})
            }
    )
    public static void getAll(Context ctx) {
        ctx.status(200).json(transferProjection.getAll());
    }

    @OpenApi(
            path = "/transfers/:id",
            method = HttpMethod.GET,
            tags = {"Transfer"},
            summary = "Get transfer by id",
            operationId = "getTransferById",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Transfer.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = NotFoundResponse.class)})
            }
    )
    public static void getOne(Context ctx) {
        UUID transferId = UUID.fromString(ctx.pathParam("id"));
        Transfer transfer = transferProjection.getOne(transferId);
        if (transfer == null) {
            throw new NotFoundResponse("Transfer doesn't exist");
        }
        ctx.status(200).json(transfer);
    }
}
