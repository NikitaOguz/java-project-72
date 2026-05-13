package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
