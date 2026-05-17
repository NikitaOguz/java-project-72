package gg.jte.generated.ondemand.urls;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,18,18,18,21,21,21,24,24,34,34,34,39,39,39,46,46,46,56,56,56,56,89,89,93,93,93,95,95,95,98,102,102,102,102,102,102,106,110,110,110,110,110,110,114,118,118,118,118,118,118,122,122,122,127,127,135,135,135,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.model.Url url, String flash, java.util.List<hexlet.code.model.UrlCheck> checks) {
		jteOutput.writeContent("\n<!doctype html>\n<html>\n\n<head>\n    <meta charset=\"UTF-8\">\n\n    <link\n            href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\n            rel=\"stylesheet\"\n    >\n</head>\n\n<body class=\"container mt-5\">\n\n");
		if (flash != null) {
			jteOutput.writeContent("\n\n    <div class=\"alert alert-info\">\n        ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(flash);
			jteOutput.writeContent("\n    </div>\n\n");
		}
		jteOutput.writeContent("\n\n<h1>URL</h1>\n\n<table class=\"table\" data-test=\"url\">\n\n    <tbody>\n\n    <tr>\n        <td>ID</td>\n        <td>");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getId());
		jteOutput.writeContent("</td>\n    </tr>\n\n    <tr>\n        <td>Имя</td>\n        <td>");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getName());
		jteOutput.writeContent("</td>\n    </tr>\n\n    <tr>\n        <td>Дата создания</td>\n\n        <td>\n            ");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getCreatedAt().toString());
		jteOutput.writeContent("\n        </td>\n    </tr>\n\n    </tbody>\n\n</table>\n\n<form\n        method=\"post\"\n        action=\"/urls/");
		jteOutput.setContext("form", "action");
		jteOutput.writeUserContent(url.getId());
		jteOutput.setContext("form", null);
		jteOutput.writeContent("/checks\"\n>\n\n    <input\n            type=\"submit\"\n            value=\"Запустить проверку\"\n            class=\"btn btn-primary\"\n    >\n\n</form>\n\n<h2 class=\"mt-5\">Проверки</h2>\n\n<table\n        class=\"table mt-4\"\n        data-test=\"checks\"\n>\n\n    <thead>\n\n    <tr>\n        <th>ID</th>\n        <th>Код ответа</th>\n        <th>h1</th>\n        <th>title</th>\n        <th>description</th>\n        <th>Дата создания</th>\n    </tr>\n\n    </thead>\n\n    <tbody>\n\n    ");
		for (var check : checks) {
			jteOutput.writeContent("\n\n        <tr>\n\n            <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(check.getId());
			jteOutput.writeContent("</td>\n\n            <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(check.getStatusCode());
			jteOutput.writeContent("</td>\n\n            <td>\n                ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(
        check.getH1().length() > 200
            ? check.getH1().substring(0, 200) + "..."
            : check.getH1()
    );
			jteOutput.writeContent("\n            </td>\n\n            <td>\n                ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(
        check.getTitle().length() > 200
            ? check.getTitle().substring(0, 200) + "..."
            : check.getTitle()
    );
			jteOutput.writeContent("\n            </td>\n\n            <td>\n                ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(
        check.getDescription().length() > 200
            ? check.getDescription().substring(0, 200) + "..."
            : check.getDescription()
    );
			jteOutput.writeContent("\n            </td>\n\n            <td>\n                ");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(check.getCreatedAt().toString());
			jteOutput.writeContent("\n            </td>\n\n        </tr>\n\n    ");
		}
		jteOutput.writeContent("\n\n    </tbody>\n\n</table>\n\n</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.model.Url url = (hexlet.code.model.Url)params.get("url");
		String flash = (String)params.get("flash");
		java.util.List<hexlet.code.model.UrlCheck> checks = (java.util.List<hexlet.code.model.UrlCheck>)params.get("checks");
		render(jteOutput, jteHtmlInterceptor, url, flash, checks);
	}
}
