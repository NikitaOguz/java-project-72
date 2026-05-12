package hexlet.code;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.net.URI;
import java.util.Map;

public class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        // Главная
        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        // Добавление URL
        app.post("/urls", ctx -> {

            String input = ctx.formParam("url");

            try {
                var url = new URI(input).toURL();

                String normalized = url.getProtocol()
                        + "://"
                        + url.getHost()
                        + (url.getPort() != -1 ? ":" + url.getPort() : "");

                var existing = UrlRepository.findByName(normalized);

                if (existing.isPresent()) {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                    ctx.redirect("/urls/" + existing.get().getId());
                    return;
                }

                var saved = UrlRepository.save(new Url(normalized));

                ctx.sessionAttribute("flash", "Страница успешно добавлена");

                ctx.redirect("/urls/" + saved.getId());

            } catch (Exception e) {
                ctx.status(422);

                ctx.render(
                        "index.jte",
                        Map.of("flash", "Некорректный URL")
                );
            }
        });

        // Список URL
        app.get("/urls", ctx -> {

            var urls = UrlRepository.getAll();

            ctx.render(
                    "urls/index.jte",
                    Map.of("urls", urls)
            );
        });

        // Один URL
        app.get("/urls/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            var url = UrlRepository.find(id).orElseThrow();

            ctx.render(
                    "urls/show.jte",
                    Map.of(
                            "url", url,
                            "flash", ctx.sessionAttribute("flash")
                    )
            );

            ctx.sessionAttribute("flash", null);
        });

        return app;
    }

    private static TemplateEngine createTemplateEngine() {

        ClassLoader classLoader = App.class.getClassLoader();

        var resolver = new ResourceCodeResolver(
                "templates",
                classLoader
        );

        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static void main(String[] args) {

        var app = getApp();

        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        app.start(port);
    }
}
