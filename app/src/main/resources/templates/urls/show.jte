@param hexlet.code.model.Url url
@param String flash

<!doctype html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container mt-5">

@if(flash != null)
    <div class="alert alert-success">
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
        <td>${url.getCreatedAt().toString()}</td>
    </tr>

    </tbody>

</table>

</body>
</html>