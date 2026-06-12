package gg.jte.generated.ondemand.urls;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,4,4,4,4,6,6,8,8,8,10,10,20,20,20,25,25,25,32,32,32,42,42,42,42,75,75,79,79,79,81,81,81,84,87,87,87,87,87,91,94,94,94,94,94,98,101,101,101,101,101,105,105,105,110,110,115,115,115,115,115,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.model.Url url, String flash, java.util.List<hexlet.code.model.UrlCheck> checks) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.JtelayoutGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n\r\n    ");
				if (flash != null) {
					jteOutput.writeContent("\r\n        <div class=\"alert alert-info\">\r\n            ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(flash);
					jteOutput.writeContent("\r\n        </div>\r\n    ");
				}
				jteOutput.writeContent("\r\n\r\n    <h1>URL</h1>\r\n\r\n    <table class=\"table\" data-test=\"url\">\r\n\r\n    <tbody>\r\n\r\n    <tr>\r\n        <td>ID</td>\r\n        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getId());
				jteOutput.writeContent("</td>\r\n    </tr>\r\n\r\n    <tr>\r\n        <td>Имя</td>\r\n        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getName());
				jteOutput.writeContent("</td>\r\n    </tr>\r\n\r\n    <tr>\r\n        <td>Дата создания</td>\r\n\r\n        <td>\r\n            ");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getCreatedAt().toString());
				jteOutput.writeContent("\r\n        </td>\r\n    </tr>\r\n\r\n    </tbody>\r\n\r\n</table>\r\n\r\n<form\r\n        method=\"post\"\r\n        action=\"/urls/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(url.getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("/checks\"\r\n>\r\n\r\n    <input\r\n            type=\"submit\"\r\n            value=\"Запустить проверку\"\r\n            class=\"btn btn-primary\"\r\n    >\r\n\r\n</form>\r\n\r\n<h2 class=\"mt-5\">Проверки</h2>\r\n\r\n<table\r\n        class=\"table mt-4\"\r\n        data-test=\"checks\"\r\n>\r\n\r\n    <thead>\r\n\r\n    <tr>\r\n        <th>ID</th>\r\n        <th>Код ответа</th>\r\n        <th>h1</th>\r\n        <th>title</th>\r\n        <th>description</th>\r\n        <th>Дата создания</th>\r\n    </tr>\r\n\r\n    </thead>\r\n\r\n    <tbody>\r\n\r\n    ");
				for (var check : checks) {
					jteOutput.writeContent("\r\n\r\n        <tr>\r\n\r\n            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getId());
					jteOutput.writeContent("</td>\r\n\r\n            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getStatusCode());
					jteOutput.writeContent("</td>\r\n\r\n            <td>\r\n                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getH1() == null ? "" :
    (check.getH1().length() > 200
        ? check.getH1().substring(0, 200) + "..."
        : check.getH1()));
					jteOutput.writeContent("\r\n            </td>\r\n\r\n            <td>\r\n                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getTitle() == null ? "" :
    (check.getTitle().length() > 200
        ? check.getTitle().substring(0, 200) + "..."
        : check.getTitle()));
					jteOutput.writeContent("\r\n            </td>\r\n\r\n            <td>\r\n                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getDescription() == null ? "" :
    (check.getDescription().length() > 200
        ? check.getDescription().substring(0, 200) + "..."
        : check.getDescription()));
					jteOutput.writeContent("\r\n            </td>\r\n\r\n            <td>\r\n                ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getCreatedAt().toString());
					jteOutput.writeContent("\r\n            </td>\r\n\r\n        </tr>\r\n\r\n    ");
				}
				jteOutput.writeContent("\r\n\r\n    </tbody>\r\n\r\n</table>\r\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.model.Url url = (hexlet.code.model.Url)params.get("url");
		String flash = (String)params.get("flash");
		java.util.List<hexlet.code.model.UrlCheck> checks = (java.util.List<hexlet.code.model.UrlCheck>)params.get("checks");
		render(jteOutput, jteHtmlInterceptor, url, flash, checks);
	}
}
