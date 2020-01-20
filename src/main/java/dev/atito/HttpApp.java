package dev.atito;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;

public class HttpApp {
    public static Javalin getApp() {
        return Javalin.create(config -> {
            config.registerPlugin(getConfiguredOpenApiPlugin());
            config.defaultContentType = "application/json";
        }).start(8080);
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").description("Money Transfers API");
        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor("")
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger-ui"));
        return new OpenApiPlugin(options);
    }
}
