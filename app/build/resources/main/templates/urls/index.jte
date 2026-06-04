@param java.util.List<hexlet.code.model.Url> urls
@param java.util.Map<Long, Integer> statuses

<table class="table" data-test="urls">

    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Дата создания</th>
        <th>Код ответа</th>
    </tr>
    </thead>

    <tbody>

    @for(var url : urls)

        <tr>
            <td>${url.getId()}</td>

            <td>
                <a href="/urls/${url.getId()}">
                    ${url.getName()}
                </a>
            </td>

            <td>
               ${url.getCreatedAt().toLocalDateTime().toLocalDate().toString()}
            </td>

            <td>
                @if(statuses.get(url.getId()) != null)
                    ${statuses.get(url.getId())}
                @endif
            </td>
        </tr>

    @endfor

    </tbody>

</table>
