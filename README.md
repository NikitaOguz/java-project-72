### Hexlet tests and linter status:
[![Actions Status](https://github.com/NikitaOguz/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/NikitaOguz/java-project-72/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72&metric=bugs)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=NikitaOguz_java-project-72&metric=coverage)](https://sonarcloud.io/summary/new_code?id=NikitaOguz_java-project-72)

Проект: Анализатор страниц (Java)

Приложение принимает URL сайта, выполняет анализ страницы и извлекает основные SEO-метаданные: title, h1 и description, после чего сохраняет результаты в базу данных. 

Проект демонстрирует навыки разработки веб-приложений на Javalin, работы с SQL-запросами и базами данных, создания HTML-шаблонов с использованием Bootstrap, а также написания тестов, включая unit- и integration-тестирование с применением Mockito.

Используемые технологии
Java 21
Javalin
Mockito
Gradle
JUnit 5

Запуск проекта
Клонирование репозитория
git clone https://github.com/NikitaOguz/java-project-72.git
cd java-project-72

Команды для запуска:
make build
make run

После запуска приложение будет доступно по адресу:

http://localhost:7070

На главной странице введите адрес проверяемого сайта и нажмите на кнопку "Проверить".

После добавления сайта у вас открывается страница всех добавленных вами сайтов.

На данной странице вы можете нажать на кнопку "Запустить проверку" и снизу просмотреть полученные вами данные проверки

Ссылка на рабочий проект из Render:
https://java-project-72-lror.onrender.com/
