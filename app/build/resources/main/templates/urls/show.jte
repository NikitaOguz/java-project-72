@param hexlet.code.model.Url url
@param String flash
@param java.util.List<hexlet.code.model.UrlCheck> checks

<!doctype html>
<html>

<head>
    <meta charset="UTF-8">

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
    >
</head>

<body class="container mt-5">

@if(flash != null)

    <div class="alert alert-info">
        ${flash}
    </div>

@endif

<h1>URL</h1>

<table class="table" data-test="url">

    <tbody>

    <tr>
        <td>ID</td>
        <td>${url.getId()}</td>
    </tr>

    <tr>
        <td>Имя</td>
        <td>${url.getName()}</td>
    </tr>

    <tr>
        <td>Дата создания</td>

        <td>
            ${url.getCreatedAt().toString()}
        </td>
    </tr>

    </tbody>

</table>

<form
        method="post"
        action="/urls/${url.getId()}/checks"
>

    <input
            type="submit"
            value="Запустить проверку"
            class="btn btn-primary"
    >

</form>

<h2 class="mt-5">Проверки</h2>

<table
        class="table mt-4"
        data-test="checks"
>

    <thead>

    <tr>
        <th>ID</th>
        <th>Код ответа</th>
        <th>h1</th>
        <th>title</th>
        <th>description</th>
        <th>Дата создания</th>
    </tr>

    </thead>

    <tbody>

    @for(var check : checks)

        <tr>

            <td>${check.getId()}</td>

            <td>${check.getStatusCode()}</td>

            <td>
                ${
        check.getH1().length() > 200
            ? check.getH1().substring(0, 200) + "..."
            : check.getH1()
    }
            </td>

            <td>
                ${
        check.getTitle().length() > 200
            ? check.getTitle().substring(0, 200) + "..."
            : check.getTitle()
    }
            </td>

            <td>
                ${
        check.getDescription().length() > 200
            ? check.getDescription().substring(0, 200) + "..."
            : check.getDescription()
    }
            </td>

            <td>
                ${check.getCreatedAt().toString()}
            </td>

        </tr>

    @endfor

    </tbody>

</table>

</body>
</html>
