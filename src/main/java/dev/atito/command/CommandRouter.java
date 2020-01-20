package dev.atito.command;

import io.javalin.Javalin;

public class CommandRouter {
    public static void init(Javalin app) {
        app.post("/requests", RequestCommandController::create);
    }
}
