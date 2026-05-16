@import hexlet.code.dto.BasePage
@import hexlet.code.route.Route
@import gg.jte.Content
@param Content content
@param BasePage page = null

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Анализатор страниц в description">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">

</head>
<body>
<!--  <nav class="fixed-top text-start border p-3 bg-light"> -->

<nav class="text-start border p-3 bg-light">
    <div class="btn-group" role="group" aria-label="Простой пример">
        <button type="button" class="btn btn-secondary disabled" tabindex="-1" role="button" aria-disabled="true">
            Анализатор страниц
        </button>
        <a href="${Route.rootPath()}" type="button" class="btn btn-secondary">Главная</a>
        <a href="${Route.urlsPath()}" type="button" class="btn btn-secondary">Сайты</a>
    </div>
</nav>

 @if(page.getFlashType() != null && page.getFlashMessage() != null)
    @if(page.getFlashType().equals("info"))
        <!-- голубое инфо alert-info-->
        <div class="rounded-0 m-0 alert alert-dismissible fade show alert-info" role="alert">
            <p class="m-0">${page.getFlashMessage()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @elseif(page.getFlashType().equals("danger"))
        <!-- красная при ошибках  alert-danger -->
        <div class="rounded-0 m-0 alert alert-dismissible fade show alert-danger" role="alert">
            <p class="m-0">${page.getFlashMessage()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @elseif(page.getFlashType().equals("success"))
        <!-- зеленая все хорошо alert-success -->
        <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
            <p class="m-0">${page.getFlashMessage()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    @endif
 @endif


<div>
    ${content}
</div>

<footer class="fixed-bottom text-center border p-3 bg-light">
    <a href="https://github.com/Dangerwind" target="_blank" class="btn btn-secondary"
       tabindex="-1" role="button" aria-disabled="true">
        Сайт разработан мной в 2025 году
    </a>
</footer>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

</body>
</html>