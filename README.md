#  Приложение для управления контактами 📞


## Обзор 🌍
- Приложение для управления контактами представляет собой небольшую консольную программу, предназначенную для удобной работы с информацией о контактах.  
- Для повышения удобства использования разработан фронтенд, который, после запуска приложения доступен по адресу http://localhost:8080 

## Возможности 🧩
- Просмотр всех доступных контактов 🔍
- Добавление нового контакта 🖋️
- Удаление контакта по адресу электронной почты 📧
- Сохранение контактов в файл 📁
- Инициализация контактов из файла (опционально, зависит от профиля приложения) 🔄
## Необходимое ПО 🔧
- Java 17 💻
- Maven (для сборки приложения) 📦
- Spring Boot 3.2.3 🚂
## Установка и настройка 🛠️
- Клонируйте репозиторий:
- git clone [url_репозитория]
- Перейдите в директорию проекта:
- Соберите приложение с помощью Maven:
```bash
mvn clean install
```
## Запустите приложение: 🔄
- Для общего использования:
- Работа с базой данных осуществляется через JdbcTemplate.
- Запуск и конфигурация базы данных через Docker.
- Чтобы запустить приложение с использованием Docker, введите следующие команды в терминале:
```bash
cd docker
```
```bash
docker-compose up
```
## Инструкция по использованию 📃
- После запуска приложения следуйте инструкциям на экране для взаимодействия со списком контактов. Приложение поддерживает следующие команды:
- Просмотреть контакты: Выведет все контакты в формате "Полное Имя | Номер Телефона | Адрес Электронной Почты".
- Добавить контакт: Предложит ввести полное имя, номер телефона и адрес электронной почты для добавления нового контакта.
- Удалить контакт: Предложит ввести адрес электронной почты контакта, которого вы хотите удалить.
- Управление контактами 
- Управление контактами осуществляется через простой интерфейс командной строки. Ошибки ввода обрабатываются корректно, предлагая правильное вводимое значение.

## Используемые технологии 🛠️
- Java 
- Spring 
- Docker 
- JdbcTemplate 
- Thymeleaf 

____
✉ Почта для обратной связи:
<a href="">krp77@mail.ru</a>