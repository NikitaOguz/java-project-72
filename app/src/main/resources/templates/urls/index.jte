@param java.util.List<hexlet.code.model.Url> urls
@param java.util.Map<Long, hexlet.code.model.UrlCheck> latestChecks
@param String flash

@template.layout(content = @`

@if(flash != null)
    <div class="alert alert-success">
        ${flash}
    </div>
@endif
    @if(urls.isEmpty())

        <p class="text-muted">
            Проверка еще не проводилась
        </p>

    @else
<table class="table" data-test="urls">

    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Последняя проверка</th>
        <th>Код ответа</th>
    </tr>
    </thead>

    <tbody>

    @for(var url : urls)

        !{var check = latestChecks.get(url.getId());}

        <tr>
            <td>${url.getId()}</td>

            <td>
                <a href="/urls/${url.getId()}">
                    ${url.getName()}
                </a>
            </td>

            <td>
                ${url.getCreatedAt().toLocalDate().toString()}
            </td>

            <td>
                @if(check != null)
                    ${check.getStatusCode()}
                @endif
            </td>

        </tr>

    @endfor

    </tbody>
</table>
    @endif
`)
