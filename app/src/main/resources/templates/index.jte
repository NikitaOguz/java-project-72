@import hexlet.code.dto.BasePage
@param hexlet.code.dto.BasePage page

@template.page.page(
page = page,
content = @`
<div class="jumbotron d-flex align-items-center min-vh-100 ">
    <div class="container text-center border rounded border p-3 bg-light " >
        <form action="/urls" method="post" class="g-3">
            <div class="mb-3">
                <div class="form-text text-center">
                    <h1>Бесплатная проверка сайтов на SEO пригодность.</h1>
                        Введите ссылку для проверки.
                </div>
                <label for="floatingInput"></label>
                <input type="url" name="url" autofocus class="form-control rounded-pill" id="floatingInput"
                       placeholder="https://www.example.com" autocomplete="off">
            </div>
            <button type="submit" class="btn btn-secondary">Отправить на проверку</button>

        </form>
    </div>
</div>
`
))