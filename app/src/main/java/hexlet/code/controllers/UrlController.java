package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import io.javalin.http.Context;

import hexlet.code.model.UrlCheck;

import kong.unirest.Unirest;

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class UrlController {

    public static void create(Context ctx) {

        String input = ctx.formParam("url");

        try {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException();
            }

            var uri = new java.net.URL(input);

            String scheme = uri.getProtocol();
            String host = uri.getHost();

            if (scheme == null || host == null ||
                    (!scheme.equals("http") && !scheme.equals("https"))) {
                throw new IllegalArgumentException();
            }

            String normalized = scheme + "://" + host;

            Url existing = UrlRepository.findByName(normalized);

            if (existing != null) {

                var checks = UrlCheckRepository.findByUrlId(existing.getId());

                var page = new HashMap<String, Object>();

                page.put("url", existing);
                page.put("checks", checks);
                page.put("flash", "Страница уже существует");

                ctx.render("urls/show.jte", page);

                return;
            }

            Url url = new Url(normalized);
            UrlRepository.save(url);

            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect("/urls/" + url.getId());

        } catch (Exception e) {
            ctx.status(422);
            ctx.render("index.jte", Map.of("flash", "Некорректный URL"));
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
        var checks = UrlCheckRepository.findByUrlId(id);

        var page = new HashMap<String, Object>();

        page.put("url", url);
        page.put("checks", checks);
        page.put("flash", ctx.queryParam("flash"));

        ctx.render("urls/show.jte", page);
    }

    public static void createCheck(Context ctx) throws Exception {
        Long id = Long.valueOf(ctx.pathParam("id"));
        Url url = UrlRepository.find(id);

        try {
            assert url != null;

            var response = Unirest.get(url.getName()).asString();

            if (response.getStatus() >= 400) {
                throw new Exception();
            }

            var document = Jsoup.parse(response.getBody());

            var check = new UrlCheck();
            check.setUrlId(id);
            check.setStatusCode(response.getStatus());
            check.setTitle(document.title());

            var h1Element = document.selectFirst("h1");
            check.setH1(h1Element != null ? h1Element.text() : "");

            var descriptionElement = document.selectFirst("meta[name=description]");
            check.setDescription(
                    descriptionElement != null
                            ? descriptionElement.attr("content")
                            : ""
            );

            UrlCheckRepository.save(check);

            String message = URLEncoder.encode(
                    "Страница успешно проверена",
                    StandardCharsets.UTF_8
            );

            ctx.redirect("/urls/" + id + "?flash=" + message);

        } catch (Exception e) {
            String message = URLEncoder.encode(
                    "Произошла ошибка при проверке",
                    StandardCharsets.UTF_8
            );

            ctx.redirect("/urls/" + id + "?flash=" + message);
        }
    }
}

