# Дипломный проект по профессии «Тестировщик»
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
##Описание приложения
Приложение представляет из себя веб-сервис.

Приложение предлагает купить тур по определённой цене с помощью двух способов:

* Обычная оплата по дебетовой карте
* Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* сервису платежей;
* кредитному сервису.

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

#Запуск тестов
1. Cклонировать [репозиторий](https://github.com/DispUrr/AQADiploma) командой `git clone`;
1. Запустить контейнер с MySql, PostgreSQL и Node.js, используя команду `docker-compose up -d --build`;
1. Запустить приложение:
    * для запуска под MySQL использовать команду:
`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar`;
    * для запуска под PostgreSQL использовать команду:
`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar`.
1. Запустить тесты:
    * для запуска под MySQL использовать команду:
`gradlew -Ddb.url=jdbc:mysql://localhost:3306/app clean test`;
    * для запуска под PostgreSQL использовать команду:
`gradlew -Ddb.url=jdbc:postgresql://localhost:5432/app clean test`.
1. Для формирования отчета Allure ввести команду `gradlew allureServe`;
1. Для работы с другой БД сначала необходимо выполнить остановку работы приложения, нажав `Ctrl+С` во вкладке терминала, в которой было запущено приложение.
1. После окончания тестов завершить работу контейнеров командой `docker-compose down`.


