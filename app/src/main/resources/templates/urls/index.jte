@param java.util.List<hexlet.code.model.Url> urls

<!doctype html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Анализатор сайтов</title>
</head>

<body class="container mt-5">

<h1>Сайты</h1>

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

            <td>200</td>
        </tr>

    @endfor

    </tbody>

</table>

</body>
</html>