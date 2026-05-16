package hexlet.code.controllers;

import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsCheckPage;
import hexlet.code.dto.BasePage;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import hexlet.code.route.Route;

import io.javalin.http.Context;
import static io.javalin.rendering.template.TemplateUtil.model;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;

import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import kong.unirest.UnirestException;
import org.jsoup.Jsoup;

public class UrlController {

    public static void root(Context ctx) {
        var page = new BasePage();
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        page.setFlashMessage(ctx.consumeSessionAttribute("flashMessage"));
        ctx.render("index.jte", model("page", page));
    }

    public static void checkPath(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var urlNameForCheck = UrlRepository.findById(id)
                .orElseThrow(() -> new  NotFoundResponse("ID не найден"));
        try {
            HttpResponse<String> response = Unirest
                    .get(urlNameForCheck.getName())
                    .asString();
            var body = Jsoup.parse(response.getBody());
            var statusCheck = response.getStatus();
            var titleText = body.title();
            var getSome = body.selectFirst("h1");
            var h1 = getSome != null ? getSome.text() : "";

            getSome = body.selectFirst("meta[name=description]");
            var description = getSome != null ? getSome.attr("content") : "";

            var createAt = LocalDateTime.now();
            var rr1 = new UrlCheck(statusCheck, titleText, h1,
                    description, id, createAt);

            UrlCheckRepository.save(rr1);
            ctx.sessionAttribute("flashMessage", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "info");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flashMessage", "Некорректный адрес");
            ctx.sessionAttribute("flashType", "danger");
        }
        ctx.redirect(Route.urlPath(id));
    }

    public static void addUrl(Context ctx) throws SQLException {
        var urlsName = ctx.formParamAsClass("url", String.class).get();

        URL uri = null;
        try {
            assert urlsName != null;
            uri = new URI(urlsName).toURL();
        } catch (Exception e) {
            ctx.sessionAttribute("flashMessage", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect(Route.rootPath());
            return;
        }

        String protocol = uri.getProtocol();
        String host = uri.getHost();
        int port = uri.getPort();
        String newUrl = protocol + "://" + host + ((port == -1 ? "" : (":" + port)));

        if (UrlRepository.findByName(newUrl).isPresent()) {
            ctx.sessionAttribute("flashType", "info");
            ctx.sessionAttribute("flashMessage", "Страница уже существует");
            ctx.redirect(Route.rootPath());
            return;
        }
        var ldt = LocalDateTime.now();
        var myUrl = new Url(newUrl, ldt);
        UrlRepository.save(myUrl);  // сохранили его
        ctx.sessionAttribute("flashMessage", "Страница успешно добавлена");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(Route.urlsPath());
    }

    public static void showUrls(Context ctx) throws SQLException {
        var allUrls = UrlRepository.getEntities();
        var lastCheck = UrlCheckRepository.findLast();
        var page = new UrlsCheckPage(allUrls, lastCheck);
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        page.setFlashMessage(ctx.consumeSessionAttribute("flashMessage"));
        ctx.render("url.jte", model("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("404, Not Found, id=" + id + " is wrong!"));
        var urls = UrlCheckRepository.findById(id);
        var page = new UrlPage(url, urls);

        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        page.setFlashMessage(ctx.consumeSessionAttribute("flashMessage"));
        ctx.render("show.jte", model("page", page));
    }
}
