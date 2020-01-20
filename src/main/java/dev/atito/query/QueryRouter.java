package dev.atito.query;

import io.javalin.Javalin;

public class QueryRouter {
    public static void init(Javalin app) {
        app.get("/accounts", AccountQueryController::getAll);
        app.get("/accounts/:id", AccountQueryController::getOne);

        app.get("/requests", RequestQueryController::getAll);
        app.get("/requests/:id", RequestQueryController::getOne);

        app.get("/transfers", TransferQueryController::getAll);
        app.get("/transfers/:id", TransferQueryController::getOne);

        app.get("/history", HistoryQueryController::getAll);
    }
}
