@import hexlet.code.dto.UrlsCheckPage
@import hexlet.code.route.Route

@import hexlet.code.dto.UrlsCheckPage

@param UrlsCheckPage page


@template.page.page(
page = page,
content = @`

<div class="jumbotron d-flex align-items-center min-vh-100 ">
    <div class="container text-center border rounded border p-3 bg-light " >
        <h1>Сайты </h1>

        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Имя</th>
                <th scope="col">Последняя проверка</th>
                <th scope="col">Код ответа</th>
            </tr>
            </thead>
            @for(var product : page.getUrls())
                <tr>
                    <td>
                            ${product.getId()}
                    </td>
                    <td>
                        <a href="${Route.urlPath(product.getId())}">
                            ${product.getName()}
                        </a>
                    </td>
                    @if(page.getLastCheck().containsKey(product.getId()))
                        <td>
                                ${page.getLastCheck().get(product.getId()).getCreatedAt().format(page.getFormatter())}
                        </td>
                        <td>
                                ${page.getLastCheck().get(product.getId()).getStatusCode()}
                        </td>
                    @else
                        <td>
                            <p> Проверка не проводилась </p>
                        </td>
                        <td>
                            <p> Проверка не проводилась </p>
                        </td>
                    @endif

                </tr>
            @endfor

        </table>

    </div>
</div>
`,
))