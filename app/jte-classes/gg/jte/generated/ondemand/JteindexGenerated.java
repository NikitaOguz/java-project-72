package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {47,47,47,47,47,47,47,47,47,47,47};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!doctype html>\r\n<html lang=\"ru\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Анализатор сайтов</title>\r\n\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n\r\n<body>\r\n\r\n<nav class=\"navbar navbar-light bg-light mb-4\">\r\n    <div class=\"container\">\r\n        <a class=\"navbar-brand\" href=\"/\">Анализатор сайтов</a>\r\n    </div>\r\n</nav>\r\n\r\n<main class=\"container\">\r\n\r\n    <div class=\"row\">\r\n        <div class=\"col-12 col-md-10 col-lg-8 mx-auto border rounded-3 bg-light p-5\">\r\n            <h1 class=\"display-3\">Анализатор страниц</h1>\r\n            <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\r\n\r\n            <form action=\"/urls\" method=\"post\" class=\"row\">\r\n                <div class=\"col-8\">\r\n                    <input\r\n                            type=\"text\"\r\n                            name=\"url\"\r\n                            class=\"form-control form-control-lg\"\r\n                            placeholder=\"https://www.example.com\"\r\n                    >\r\n                </div>\r\n\r\n                <div class=\"col-2\">\r\n                    <input type=\"submit\"\r\n                           class=\"btn btn-primary btn-lg ms-3 px-5 text-uppercase mx-3\"\r\n                           value=\"Проверить\">\r\n                </div>\r\n            </form>\r\n\r\n        </div>\r\n    </div>\r\n\r\n</main>\r\n\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
