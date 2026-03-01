package hexlet.code;

import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "8080");
        return Integer.valueOf(port);
    }

    public static Javalin getApp() {
        return Javalin.create(config -> {
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        });
    }

    public static void main(String[] args) {
        getApp().start(getPort());
    }
}
