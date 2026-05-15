package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import io.javalin.http.Context;

import hexlet.code.model.UrlCheck;

import kong.unirest.Unirest;

import org.jsoup.Jsoup;
import java.net.URL;

import java.util.HashMap;

public class UrlController {

    public static void create(Context ctx) throws Exception {
        String input = ctx.formParam("url");

        try {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException();
            }

            URL parsedUrl = new URL(input);
            String normalized = parsedUrl.getProtocol() + "://" + parsedUrl.getHost();

            Url existing = UrlRepository.findByName(normalized);

            if (existing != null) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect("/urls/" + existing.getId());
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

        Long id = Long.valueOf(
                ctx.pathParam("id")
        );

        Url url = UrlRepository.find(id);

        var checks = UrlCheckRepository.findByUrlId(id);

        var page = new HashMap<String, Object>();

        page.put("url", url);

        page.put("checks", checks);

        page.put(
                "flash",
                ctx.consumeSessionAttribute("flash")
        );

        ctx.render("urls/show.jte", page);
    }
    public static Url findUrl(Long id) throws Exception {
        return UrlRepository.find(id);
    }
    public static void createCheck(Context ctx) throws Exception {

        Long id = Long.valueOf(ctx.pathParam("id"));

        Url url = UrlRepository.find(id);

        try {

            var response = Unirest
                    .get(url.getName())
                    .asString();

            var document = Jsoup.parse(response.getBody());

            var check = new UrlCheck();

            check.setUrlId(id);

            check.setStatusCode(response.getStatus());

            String title = document.title();

            var h1Element = document.selectFirst("h1");

            var descriptionElement = document.selectFirst(
                    "meta[name=description]"
            );

            check.setTitle(title);

            check.setH1(
                    h1Element != null
                            ? h1Element.text()
                            : ""
            );

            check.setDescription(
                    descriptionElement != null
                            ? descriptionElement.attr("content")
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
    }
}

