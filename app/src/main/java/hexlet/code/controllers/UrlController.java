package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import io.javalin.http.Context;

import java.net.URI;
import java.net.URL;

import java.util.HashMap;

public class UrlController {

    public static void create(Context ctx) throws Exception {

        String input = ctx.formParam("url");

        try {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException();
            }

            URI uri = new URI(input);

            if (uri.getScheme() == null || uri.getHost() == null) {
                throw new IllegalArgumentException();
            }

            String normalized = uri.getScheme() + "://" + uri.getHost();

            Url existing = UrlRepository.findByName(normalized);

            if (existing != null) {
                var page = new HashMap<String, Object>();
                page.put("url", existing);
                page.put("flash", "Страница уже существует");

                ctx.status(200);
                ctx.render("urls/show.jte", page);
                return;
            }

            Url url = new Url(normalized);
            UrlRepository.save(url);

            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect("/urls/" + url.getId());

        } catch (Exception e) {
            ctx.status(422);

            var page = new HashMap<String, Object>();
            page.put("flash", "Некорректный URL");

            ctx.render("index.jte", page);
        }
    }

    public static void index(Context ctx) throws Exception {

        var page = new HashMap<String, Object>();

        page.put("urls", UrlRepository.getEntities());

        ctx.render("urls/index.jte", page);
    }

    public static void show(Context ctx) throws Exception {

        Long id = Long.valueOf(ctx.pathParam("id"));

        Url url = UrlRepository.find(id);

        var page = new HashMap<String, Object>();

        page.put("url", url);

        page.put(
                "flash",
                ctx.consumeSessionAttribute("flash")
        );

        ctx.render("urls/show.jte", page);
    }
}

