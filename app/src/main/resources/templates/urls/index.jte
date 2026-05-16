@param java.util.List<hexlet.code.model.Url> urls
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

    @for(hexlet.code.model.Url url : urls)

        @{
        var lastCheck = checksRepository.getLastCheck(url.getId());
        }

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

            {% set lastCheck = hexlet.code.repository.UrlCheckRepository.getLastCheck(url.getId()) %}
            <td>
                {{ lastCheck != null ? lastCheck.getStatusCode() : '' }}
            </td>
        </tr>

    @endfor

    </tbody>

</table>
