@param String flash

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
    <div class="alert alert-danger">
        ${flash}
    </div>
@endif

<div class="row">
    <div class="col-12 col-md-10 col-lg-8 mx-auto border rounded-3 bg-light p-5">

        <h1 class="display-3">
            Анализатор страниц
        </h1>

        <p class="lead">
            Бесплатно проверяйте сайты на SEO пригодность
        </p>

        <form action="/urls" method="post" class="row">

            <div class="col-8">

                <input
                        type="text"
                        name="url"
                        class="form-control form-control-lg"
                        placeholder="https://example.com"
                >

            </div>

            <div class="col-2">

                <input
                        type="submit"
                        class="btn btn-primary btn-lg"
                        value="Проверить"
                >

            </div>

        </form>

    </div>
</div>

</body>
</html>