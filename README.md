# Приложение для управления контактами 📞

##  Фронтенд, после запуска приложения, доступен по адресу: [http://localhost:8080/](http://localhost:8080/)

---

## Обзор 🌍
- Удобный интерфейс для работы с контактами (просмотр, добавление, редактирование, удаление).
- Бэкенд использует JdbcTemplate для работы с PostgreSQL.
- Возможен запуск через Docker для быстрого поднятия БД и сервисов.

---

## Возможности 🚀
- 👥 Просмотр всех контактов
- ✅ Добавление нового контакта
- ✏️ Редактирование контакта
- ❌ Удаление контакта

---

## Необходимое ПО 🔧
- Java 17
- Maven (для сборки)
- Docker и docker-compose (опционально, для БД)
- Spring Boot 3.2.3

---

## Установка и запуск 🛠️

Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Katas77/contactlist-thymeleaf.git
   ```
Перейдите в директорию проекта и соберите приложение:
   ```bash
    mvn clean package -DskipTests
   ```
## Запуск через Docker (рекомендуется для быстрого старта с БД):

   ```bash

docker-compose up -d
   ```
- 💡 При таком запуске создаются два независимых контейнера:
- app — ваше Spring Boot-приложение (Java),
- db — PostgreSQL.
- Они автоматически объединяются в одну виртуальную сеть и общаются по именам сервисов (jdbc:postgresql://db:5432/...).
- Доступ к приложению — по тому же адресу: http://localhost:8080/


4. Запуск базы данных через Docker (опционально):
   ```bash
   cd docker
   docker-compose up -d
   ```
Запуск приложения локально:
   ```bash
   mvn spring-boot:run
   ```

---

## Конфигурация базы данных
Работа с БД настроена через JdbcTemplate. Пример необходимых параметров в application.yml (фрагмент):

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/contacts
    username: roman
    password: roman
    hikari:
      schema: contacts_schema
```

---

## Используемые технологии 🛠️
- Java 17
- Spring Boot
- PostgreSQL
- JdbcTemplate
- Thymeleaf
- Docker / docker-compose
- HTML & CSS

---



---

## Контакты ✉
Почта для обратной связи: [krp77@mail.ru](mailto:krp77@mail.ru)

---

