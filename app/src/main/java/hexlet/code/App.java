package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.controllers.UrlController;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.util.HashMap;

public class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {

            if (System.getenv("APP_ENV") == null) {
                config.bundledPlugins.enableDevLogging();
            }

            config.fileRenderer(
                    new JavalinJte(createTemplateEngine())
            );
        });

        // Главная страница
        app.get("/", ctx -> {

            var page = new HashMap<String, Object>();

            page.put(
                    "flash",
                    ctx.consumeSessionAttribute("flash")
            );

            ctx.render("index.jte", page);
        });

        // Добавление URL
        app.post("/urls", UrlController::create);

        // Список URL
        app.get("/urls", UrlController::index);

        // Один URL
        app.get("/urls/{id}", UrlController::show);

        return app;
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

    public static void main(String[] args) {

        var app = getApp();

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        app.start(port);
    }
}
