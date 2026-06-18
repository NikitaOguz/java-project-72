package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.controllers.UrlController;

import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.HashMap;

public class App {

    public static Javalin getApp() throws Exception {

        initDataSource();

        var app = Javalin.create(config -> {

            if (System.getenv("APP_ENV") == null) {
                config.bundledPlugins.enableDevLogging();
            }

            config.fileRenderer(
                    new JavalinJte(createTemplateEngine())
            );

            config.jetty.modifyServletContextHandler(handler -> {
                var sessionHandler = new SessionHandler();

                sessionHandler.setHttpOnly(true);
                sessionHandler.setSameSite(HttpCookie.SameSite.LAX);

                handler.setSessionHandler(sessionHandler);
            });
        });

        app.before(ctx -> ctx.req().getSession(true));

        app.get("/", ctx -> {
            var page = new HashMap<String, Object>();
            page.put("flash", ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", page);
        });
        app.post("/urls", UrlController::create);
        app.get("/urls", UrlController::index);
        app.get("/urls/{id}", UrlController::show);
        app.post("/urls/{id}/checks", UrlController::createCheck);

        return app;
    }
    private static HikariDataSource dataSource;

    private static void initDataSource() throws Exception {
        if (dataSource != null) {
            return;
        }

        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1");

        dataSource = new HikariDataSource(config);
        BaseRepository.setDataSource(dataSource);

        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS urls (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) UNIQUE NOT NULL,
                    created_at TIMESTAMP NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS url_checks (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    url_id BIGINT NOT NULL,
                    status_code INT,
                    h1 TEXT,
                    title TEXT,
                    description TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (url_id) REFERENCES urls(id)
                );
            """);
        }
    }

    private static TemplateEngine createTemplateEngine() {
        var resolver = new ResourceCodeResolver("templates", App.class.getClassLoader());
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static void main(String[] args) throws Exception {
        var app = getApp();
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}

