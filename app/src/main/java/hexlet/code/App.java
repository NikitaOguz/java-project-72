package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.controllers.UrlController;

import hexlet.code.model.UrlCheck;

import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import kong.unirest.Unirest;

import org.jsoup.Jsoup;

import java.util.HashMap;

public class App {

    public static Javalin getApp() throws Exception {

        initDataSource();

        var app = Javalin.create(config -> {

            if (System.getenv("APP_ENV") == null) {
                config.bundledPlugins.enableDevLogging();
            }

            config.fileRenderer(
                    new JavalinJte(
                            createTemplateEngine()
                    )
            );
        });

        app.get("/", ctx -> {

            var page = new HashMap<String, Object>();

            page.put(
                    "flash",
                    ctx.consumeSessionAttribute("flash")
            );

            ctx.render("index.jte", page);
        });

        app.post("/urls", UrlController::create);

        app.get("/urls", UrlController::index);

        app.get("/urls/{id}", UrlController::show);

        app.post("/urls/{id}/checks", ctx -> {

            Long id = Long.parseLong(
                    ctx.pathParam("id")
            );

            var url = UrlController.findUrl(id);

            try {

                var response = Unirest
                        .get(url.getName())
                        .asString();

                var document = Jsoup.parse(
                        response.getBody()
                );

                var check = new UrlCheck();

                check.setUrlId(id);

                check.setStatusCode(
                        response.getStatus()
                );

                var h1 = document.selectFirst("h1");

                var title = document.selectFirst("title");

                var description = document.selectFirst(
                        "meta[name=description]"
                );

                check.setH1(
                        h1 != null ? h1.text() : ""
                );

                check.setTitle(
                        title != null ? title.text() : ""
                );

                check.setDescription(
                        description != null
                                ? description.attr("content")
                                : ""
                );

                UrlCheckRepository.save(check);

                ctx.sessionAttribute(
                        "flash",
                        "Страница успешно проверена"
                );

            } catch (Exception e) {

                ctx.sessionAttribute(
                        "flash",
                        "Произошла ошибка при проверке"
                );
            }

            ctx.redirect("/urls/" + id);
        });

        return app;
    }

    private static void initDataSource() throws Exception {

        var config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1"
        );

        var dataSource = new HikariDataSource(config);

        BaseRepository.setDataSource(dataSource);

        try (
                var conn = dataSource.getConnection();
                var stmt = conn.createStatement()
        ) {

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
                    created_at TIMESTAMP NOT NULL,

                    FOREIGN KEY (url_id) REFERENCES urls(id)
                );
            """);
        }
    }

    private static TemplateEngine createTemplateEngine() {

        ClassLoader classLoader = App.class.getClassLoader();

        var resolver = new ResourceCodeResolver(
                "templates",
                classLoader
        );

        return TemplateEngine.create(
                resolver,
                ContentType.Html
        );
    }

    public static void main(String[] args) throws Exception {

        var app = getApp();

        int port = Integer.parseInt(
                System.getenv()
                        .getOrDefault("PORT", "7070")
        );

        app.start(port);
    }
}

