package gg.jte.generated.ondemand;
import gg.jte.Content;
public final class JtelayoutGenerated {
	public static final String JTE_NAME = "layout.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,20,20,20,20,25,25,25,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content) {
		jteOutput.writeContent("<!doctype html>\r\n<html lang=\"ru\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Page Analyzer</title>\r\n\r\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n</head>\r\n\r\n<body>\r\n\r\n<nav class=\"navbar navbar-light bg-light mb-4\">\r\n    <div class=\"container\">\r\n        <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n    </div>\r\n</nav>\r\n\r\n<main class=\"container\">\r\n    ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n</main>\r\n\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, content);
	}
}
