package dat.config;

import dat.routes.TripRoutes;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
    private final Javalin app;

    public ApplicationConfig() {
        app = Javalin.create(this::configuration);
    }

    private void configuration(JavalinConfig config) {
        config.plugins.enableCors(cors -> cors.add(it -> {
            it.anyHost();
            it.allowCredentials = true;
            it.exposeHeader("*");
        }));
        config.http.defaultContentType = "application/json";
        config.plugins.enableDevLogging();
    }

    public void startServer(int port) {
        app.start(port);
        LOGGER.info("Server started on port: {}", port);
        new TripRoutes().addRoutes(app);
    }

    public void stopServer() {
        app.stop();
    }
}