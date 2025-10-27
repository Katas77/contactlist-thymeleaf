# Dockerfile для Spring Boot приложения (Java 17, Maven)

# Используем официальный образ OpenJDK 17 как базовый
# Образ содержит только JRE, что делает контейнер легче
FROM eclipse-temurin:17-jre-jammy

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
# Имя JAR-файла должно соответствовать вашему проекту: contactlist-0.0.1-SNAPSHOT.jar
COPY target/contactlist-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Указываем точку входа для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]



# 1. Соберите JAR-файл с помощью Maven
# mvn clean package -DskipTests
# флаг -DskipTests пропускает запуск тестов (опционально, но ускоряет сборку).


# 2. Убедитесь, что JAR-файл создан
# dir target\*.jar

# 3.  Соберите Docker-образ
# docker build -t contactlist-app .

# 4.  Запустите контейнер
# docker run -p 8080:8080 contactlist-app