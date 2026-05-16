@import hexlet.code.dto.UrlPage
@import hexlet.code.route.Route

@param UrlPage page

@template.page.page(
page = page,
content = @`

    <div class="jumbotron d-flex align-items-center min-vh-100 ">
        <div class="container text-center border rounded border p-3 bg-light " >
            <div class="row">
                <h1 class="col align-items-right">
                    Сайт: ${page.getUrl().getName()}
                    <form method="post" action="${Route.checkPath(page.getUrl().getId())}">
                        <button type="submit" class="btn btn-secondary">Запустить проверку</button>
                    </form>
                </h1>
            </div>

            <table class="table">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Дата создания</th>
                </tr>
                <tr>
                    <td>
                        ${page.getUrl().getId()}
                    </td>
                    <td>
                        ${page.getUrl().getName()}
                    </td>
                    <td>
                        ${page.getUrl().getCreatedAt().format(page.getFormatter())}
                    </td>
                </tr>
            </table>

            <h1>Проверки</h1>

            @if(page.getUrlsCheck().isEmpty())
                <h2> Проверка еще не проводилась</h2>
            @else
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Код ответа</th>
                        <th scope="col">title</th>
                        <th scope="col">h1</th>
                        <th scope="col">description</th>
                        <th scope="col">Дата проверки</th>
                    </tr>
                    </thead>
                    @for(var product : page.getUrlsCheck())
                        <tr>
                            <td>
                                ${product.getId()}
                            </td>
                            <td>
                                ${product.getStatusCode()}
                            </td>
                            <td>
                                ${product.getTitle()}
                            </td>
                            <td>
                                ${product.getH1()}
                            </td>
                            <td>
                                ${product.getDescription()}
                            </td>
                            <td>
                                ${product.getCreatedAt().format(page.getFormatter())}
                            </td>
                        </tr>
                    @endfor
                </table>

            @endif



        </div>
    </div>
`,
)