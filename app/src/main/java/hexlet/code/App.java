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

import java.util.HashMap;

public class App {

    public static Javalin getApp() throws Exception {

        initDataSource();

        var app = Javalin.create(config -> {
            if (System.getenv("APP_ENV") == null) {
                config.bundledPlugins.enableDevLogging();
            }

            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get("/", ctx -> {
            var page = new HashMap<String, Object>();
            page.put("flash", ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", page);
        });

        app.post("/urls", UrlController::create);
        app.get("/urls", UrlController::index);
        app.get("/urls/{id}", UrlController::show);

        return app;
    }

    private static void initDataSource() throws Exception {
        var config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1");

        var dataSource = new HikariDataSource(config);
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
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();

        var resolver = new ResourceCodeResolver("templates", classLoader);

        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static void main(String[] args) throws Exception {
        var app = getApp();

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        app.start(port);
    }
}

