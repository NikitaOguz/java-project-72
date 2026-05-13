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

            URL parsedUrl = new URI(input).toURL();

            String normalized =
                    parsedUrl.getProtocol()
                            + "://"
                            + parsedUrl.getAuthority();

            Url existing = UrlRepository.findByName(normalized);

            if (existing != null) {

                ctx.sessionAttribute(
                        "flash",
                        "Страница уже существует"
                );

                ctx.redirect("/urls/" + existing.getId());

                return;
            }

            Url url = new Url(normalized);

            UrlRepository.save(url);

            ctx.sessionAttribute(
                    "flash",
                    "Страница успешно добавлена"
            );

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
