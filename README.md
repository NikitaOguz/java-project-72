# Page Analyzer (Java)

### Hexlet tests and linter status

[![Actions Status](https://github.com/NikitaOguz/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/NikitaOguz/java-project-72/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72\&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72\&metric=bugs)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72\&metric=coverage)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)

---

## 📖 О проекте

**Анализатор страниц** — веб-приложение для анализа веб-страниц.

Приложение принимает URL сайта, выполняет анализ страницы и извлекает основные SEO-метаданные:

* `title`
* `h1`
* `description`

Результаты проверки сохраняются в базе данных и отображаются в удобном интерфейсе.

Проект демонстрирует навыки разработки веб-приложений на **Javalin**, работы с SQL-базами данных, создания HTML-шаблонов с использованием **JTE** и **Bootstrap**, а также написания **unit** тестов с использованием **JUnit 5** и **Mockito**.

---

## 🛠 Используемые технологии

* Java 21
* Javalin
* Gradle
* JTE Templates
* Bootstrap 5
* JDBC
* H2 Database
* Jsoup
* JUnit 5
* Mockito

---

## 🚀 Запуск проекта

### Клонирование репозитория

```bash
git clone https://github.com/NikitaOguz/java-project-72.git
cd java-project-72
```

### Сборка проекта

```bash
make build
```

### Запуск приложения

```bash
make run
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:7070
```

---

## 💻 Как пользоваться

1. Откройте главную страницу приложения.
2. Введите URL сайта и нажмите **«Проверить»**.
3. После добавления сайт появится в общем списке.
4. Перейдите на страницу нужного сайта.
5. Нажмите **«Запустить проверку»**.
6. После выполнения проверки будут отображены:

   * HTTP-код ответа;
   * заголовок страницы (`title`);
   * основной заголовок (`h1`);
   * описание (`description`);
   * дата выполнения проверки.

---

## 🌐 Демо

Рабочая версия приложения доступна по ссылке:

**https://java-project-72-lror.onrender.com/**
