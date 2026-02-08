# Fit_REST

---

Fit_REST — это RESTful API для фитнес-приложения, разработанного на Spring Boot. Оно предоставляет endpoints для управления пользователями, статьями, рецептами, планами питания, целями и другими ресурсами, связанными с фитнесом и здоровьем.


---

## 🚀 Технологии и стек

<details>
  <summary>🛠 <strong>Backend</strong></summary>

- **Java 17+**
- **Spring Boot**
- **JWT**
- **MapStruct**
- **Liquibase** / **Preliquibase**
- **Lombok**
- **PostgreSQL**
- **Redis**
- **MinIO**
</details>

<details>
  <summary>🧪 <strong>Тестирование</strong></summary>

- **JUnit Jupiter**
- **Mockito**
</details>

<details>
  <summary>📄 <strong>Документация</strong></summary>

- **Springdoc OpenAPI** + **Swagger UI**
</details>

<details>
  <summary>⚙️ <strong>Качество кода</strong></summary>

- **Checkstyle**
</details>

<details>
  <summary>📦 <strong>Сборка</strong></summary>

- **Maven**
</details>

<details>
  <summary>🐳 <strong>Контейнеризация</strong></summary>

- **Docker** / **Docker Compose**
</details>

---
## Документация API

После запуска приложения вы можете получить доступ к документации API через Swagger UI по адресу:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Swagger UI предоставляет интерактивный интерфейс для изучения всех доступных endpoints и их использования.

---

## Доступ к сервисам

- **Приложение**: [http://localhost:8080](http://localhost:8080)
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **MinIO Console**: [http://localhost:9001](http://localhost:9001) (войдите, используя учетные данные из `.env`)

## ⚙️ Инструкция запуска проекта

```bash
# 1. Клонировать репозиторий
git clone https://github.com/yattorie/Fit_REST.git
cd Fit_REST

# 2. Создать .env файл в корне проекта (пример .env.example)

# 3. Сборка проекта
mvn clean install

# 4. Запуск приложения и зависимостей
docker-compose up --build
```

---

## 🧑‍💻 Контакты

- Автор: [yattorie](https://github.com/yattorie)

