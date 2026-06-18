package hexlet.code.controllers;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UrlController {

    public static void create(Context ctx) throws Exception {

        String input = ctx.formParam("url");
        URI uri;
        try {
            uri = new URI(input);
        } catch (Exception e) {
            renderInvalidUrl(ctx);
            return;
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();

        if (scheme == null || host == null || (!scheme.equals("http") && !scheme.equals("https"))) {
            renderInvalidUrl(ctx);
            return;
        }
        String normalized = scheme + "://" + host;
        Optional<Url> existing = UrlRepository.findByName(normalized);

        if (existing.isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.redirect("/urls/" + existing.get().getId());
            return;
        }
        Url url = UrlRepository.save(new Url(normalized));
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.redirect("/urls/" + url.getId());
    }
    public static void index(Context ctx) throws Exception {

        var urls = UrlRepository.getEntities();
        var latestChecks = UrlCheckRepository.findLatestChecks();
        Map<Long, Integer> statuses = new HashMap<>();

        for (var url : urls) {
            var check = latestChecks.get(url.getId());
            statuses.put(url.getId(), check != null ? check.getStatusCode() : null);
        }

        var page = new HashMap<String, Object>();
        page.put("urls", urls);
        page.put("statuses", statuses);

        ctx.render("urls/index.jte", page);
    }
    public static void show(Context ctx) throws Exception {

        Long id = Long.valueOf(ctx.pathParam("id"));
        Url url = UrlRepository.find(id).orElseThrow(() -> new Exception("URL not found"));
        var checks = UrlCheckRepository.findByUrlId(id);
        var page = new HashMap<String, Object>();

        page.put("url", url);
        page.put("checks", checks);
        page.put("flash", ctx.consumeSessionAttribute("flash"));

        ctx.render("urls/show.jte", page);
    }
    public static void createCheck(Context ctx) throws Exception {

        Long id = Long.valueOf(ctx.pathParam("id"));
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new Exception("URL not found"));

        try {

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
            check.setDescription(descriptionElement != null ? descriptionElement.attr("content") : "");

            UrlCheckRepository.save(check);
            ctx.sessionAttribute("flash", "Страница успешно проверена");

            ctx.redirect("/urls/" + id);

        } catch (Exception e) {

            var checks = UrlCheckRepository.findByUrlId(id);
            var page = new HashMap<String, Object>();

            page.put("url", url);
            page.put("checks", checks);
            page.put("flash", "Произошла ошибка при проверке");

            ctx.render("urls/show.jte", page);
        }
    }
    private static void renderInvalidUrl(Context ctx) {
        ctx.status(422);
        ctx.render("index.jte", Map.of("flash", "Некорректный URL"));
    }
}

