package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private Javalin app;

    @BeforeEach
    public void setUp() throws Exception {

        app = App.getApp();
    }

    @Test
    public void testMainPage() {

        JavalinTest.test(app, (server, client) -> {

            var response = client.get("/");

            assertThat(response.code()).isEqualTo(200);

            assertThat(response.body().string())
                    .contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {

        JavalinTest.test(app, (server, client) -> {

            var response = client.get("/urls");

            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateUrl() {

        JavalinTest.test(app, (server, client) -> {

            var requestBody = "url=https://example.com/test";

            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);

            var urls = UrlRepository.getEntities();

            assertThat(urls.size()).isGreaterThan(0);

            assertThat(response.body().string())
                    .contains("https://example.com");
        });
    }

    @Test
    public void testShowUrl() throws Exception {

        var url = UrlRepository.save(
                new Url("https://example.com")
        );

        JavalinTest.test(app, (server, client) -> {

            var response = client.get("/urls/" + url.getId());

            assertThat(response.code()).isEqualTo(200);

            assertThat(response.body().string())
                    .contains("https://example.com");
        });
    }

    @Test
    public void testInvalidUrl() {

        JavalinTest.test(app, (server, client) -> {

            var requestBody = "url=wrong-url";

            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(422);

            assertThat(response.body().string())
                    .contains("Некорректный URL");
        });
    }

    @Test
    public void testDuplicateUrl() throws Exception {

        UrlRepository.save(
                new Url("https://example.com")
        );

        JavalinTest.test(app, (server, client) -> {

            var requestBody = "url=https://example.com/test";

            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);

            assertThat(response.body().string())
                    .contains("Страница уже существует");
        });
    }
    @Test
    public void testUrlCheckModel() {

        var check = new UrlCheck();

        check.setUrlId(1L);
        check.setStatusCode(200);
        check.setTitle("title");
        check.setH1("h1");
        check.setDescription("description");

        assertThat(check.getUrlId()).isEqualTo(1L);
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getTitle()).isEqualTo("title");
        assertThat(check.getH1()).isEqualTo("h1");
        assertThat(check.getDescription()).isEqualTo("description");
    }
    @Test
    public void testGetLastCheckMethods() throws Exception {

        var url = UrlRepository.save(
                new Url("https://example.com")
        );

        var check = new UrlCheck();

        check.setUrlId(url.getId());
        check.setStatusCode(200);
        check.setTitle("Example");
        check.setH1("Example");
        check.setDescription("Example description");

        UrlCheckRepository.save(check);

        var lastCheck = UrlCheckRepository.getLastCheck(url.getId());

        assertThat(lastCheck).isNotNull();

        var statusCode =
                UrlCheckRepository.getLastCheckStatusCode(url.getId());

        assertThat(statusCode).isEqualTo(200);
    }
}
