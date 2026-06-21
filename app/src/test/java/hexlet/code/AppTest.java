package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private Javalin app;
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws Exception {

        app = App.getApp();

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        try (
                var conn = BaseRepository.getDataSource().getConnection();
                var stmt = conn.createStatement()
        ) {
            stmt.execute("DELETE FROM url_checks");
            stmt.execute("DELETE FROM urls");
        }
    }
    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
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

            assertThat(urls).hasSize(1);

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

            var response =
                    client.post("/urls", "url=https://example.com/test");

            assertThat(response.code())
                    .isIn(200, 302);
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
    public void testFindLatestChecks() throws Exception {

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

        var latestChecks =
                UrlCheckRepository.findLatestChecks();

        assertThat(latestChecks)
                .containsKey(url.getId());

        var lastCheck =
                latestChecks.get(url.getId());

        assertThat(lastCheck.getStatusCode())
                .isEqualTo(200);
    }
    @Test
    public void testCheckPageError() throws Exception {

        var url = UrlRepository.save(
                new Url("https://wrong-domain-123123123.com")
        );

        JavalinTest.test(app, (server, client) -> {

            var response = client.post(
                    "/urls/" + url.getId() + "/checks"
            );

            assertThat(response.code())
                    .isIn(200, 302);
        });
    }
    @Test
    public void testUrlsPageWithChecks() throws Exception {
        var url = UrlRepository.save(
                new Url("https://example.com")
        );

        var check = new UrlCheck();
        check.setUrlId(url.getId());
        check.setStatusCode(200);

        UrlCheckRepository.save(check);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");

            assertThat(response.body().string())
                    .contains("200");
        });
    }
    @Test
    public void testGetDataSource() {
        assertThat(BaseRepository.getDataSource())
                .isNotNull();
    }
    @Test
    public void testCheckPageSuccess() throws Exception {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody("""
                        <html>
                            <head>
                                <title>Awesome page</title>
                                <meta name="description"
                                      content="Statements of great people">
                            </head>
                            <body>
                                <h1>Do not expect a miracle, miracles yourself!</h1>
                            </body>
                        </html>
                        """)
        );

        String testUrl = mockWebServer.url("/").toString();

        var url = UrlRepository.save(
                new Url(testUrl)
        );

        JavalinTest.test(app, (server, client) -> {

            client.post("/urls/" + url.getId() + "/checks");

            var checks =
                    UrlCheckRepository.findByUrlId(url.getId());

            assertThat(checks).hasSize(1);

            var check = checks.get(0);

            assertThat(check.getStatusCode())
                    .isEqualTo(200);

            assertThat(check.getTitle())
                    .isEqualTo("Awesome page");

            assertThat(check.getH1())
                    .isEqualTo("Do not expect a miracle, miracles yourself!");

            assertThat(check.getDescription())
                    .isEqualTo("Statements of great people");
        });
    }
    @Test
    public void testGetLastCheckStatusCode() throws Exception {

        var url = UrlRepository.save(
                new Url("https://example.com")
        );

        var check = new UrlCheck();
        check.setUrlId(url.getId());
        check.setStatusCode(200);

        UrlCheckRepository.save(check);

        Integer statusCode =
                UrlCheckRepository.getLastCheckStatusCode(url.getId());

        assertThat(statusCode)
                .isEqualTo(200);
    }
    @Test
    public void testGetLastCheckStatusCodeWithoutChecks() throws Exception {

        var url = UrlRepository.save(
                new Url("https://example.com")
        );

        Integer statusCode =
                UrlCheckRepository.getLastCheckStatusCode(url.getId());

        assertThat(statusCode).isNull();
    }
}
