package hexlet.code;

import io.javalin.Javalin;

public class App {

    public Javalin getApp() {
        return Javalin.create(config -> {
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        });
    }
}
