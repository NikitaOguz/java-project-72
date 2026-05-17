@import gg.jte.Content
@param Content content
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Page Analyzer</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-light bg-light mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">Анализатор страниц</a>
    </div>
</nav>

<main class="container">
    ${content}
</main>

</body>
</html>
