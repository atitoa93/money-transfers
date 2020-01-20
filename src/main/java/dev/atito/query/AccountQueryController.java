package dev.atito.query;

import dev.atito.event.bus.EventBus;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import dev.atito.domain.account.Account;
import dev.atito.projection.AccountProjection;

import java.util.UUID;

public class AccountQueryController {
    private static AccountProjection accountProjection = new AccountProjection();

    public static void init(EventBus eventBus) {
        eventBus.register(accountProjection);
    }

    @OpenApi(
            path = "/accounts",
            method = HttpMethod.GET,
            tags = {"Account"},
            summary = "Get all accounts",
            operationId = "getAllAccounts",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Account[].class)})
            }
    )
    public static void getAll(Context ctx) {
        ctx.status(200).json(accountProjection.getAll());
    }

    @OpenApi(
            path = "/accounts/:id",
            method = HttpMethod.GET,
            tags = {"Account"},
            summary = "Get account by id",
            operationId = "getAccountById",
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Account.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = NotFoundResponse.class)})
            }
    )
    public static void getOne(Context ctx) {
        UUID accountId = UUID.fromString(ctx.pathParam("id"));
        Account account = accountProjection.getOne(accountId);
        if (account == null) {
            throw new NotFoundResponse("Account doesn't exist");
        }
        ctx.status(200).json(account);
    }
}
