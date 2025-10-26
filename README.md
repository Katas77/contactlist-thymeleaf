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

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Katas77
   ```
   (Замените URL на точный адрес вашего репозитория при необходимости.)

2. Перейдите в директорию проекта и соберите приложение:
   ```bash
   mvn clean install
   ```

3. Запуск приложения локально:
   ```bash
   mvn spring-boot:run
   ```
   После старта фронтенд будет доступен по ссылке, указанной в начале README.

4. Запуск базы данных через Docker (опционально):
   ```bash
   cd docker
   docker-compose up -d
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
    password: skill
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

## Быстрая схема запуска
```mermaid
flowchart LR
  A[Клонировать репозиторий] --> B[Собрать (mvn clean install)]
  B --> C[Запустить (mvn spring-boot:run)]
  C --> D[Открыть UI в браузере]
  D --> E[Использовать приложение]
```

---

## Контакты ✉
Почта для обратной связи: [krp77@mail.ru](mailto:krp77@mail.ru)

---

