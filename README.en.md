# Fit_REST

---

Fit_REST is a RESTful API for a fitness application built on Spring Boot. It provides endpoints for managing users, articles, recipes, meal plans, goals, and other fitness and health-related resources.

---

## 🚀 Technologies and Stack

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
  <summary>🧪 <strong>Testing</strong></summary>

- **JUnit Jupiter**
- **Mockito**
</details>

<details>
  <summary>📄 <strong>Documentation</strong></summary>

- **Springdoc OpenAPI** + **Swagger UI**
</details>

<details>
  <summary>⚙️ <strong>Code Quality</strong></summary>

- **Checkstyle**
</details>

<details>
  <summary>📦 <strong>Build</strong></summary>

- **Maven**
</details>

<details>
  <summary>🐳 <strong>Containerization</strong></summary>

- **Docker** / **Docker Compose**
</details>

---
## API Documentation

Once the application is running, you can access the API documentation via Swagger UI at:

http://localhost:8080/swagger-ui.html

Swagger UI provides an interactive interface to explore all available endpoints and their usage.

---

## Service Access

- **Application**: [http://localhost:8080](http://localhost:8080)
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **MinIO Console**: [http://localhost:9001](http://localhost:9001) (log in using credentials from .env)

## ⚙️ Project Startup Guide

```bash
# 1. Clone the repository
git clone https://github.com/yattorie/Fit_REST.git
cd Fit_REST

# 2. Create a .env file in the root of the project (example .env.example)

# 3. Build the project
mvn clean install

# 4. Start the application and dependencies
docker-compose up --build
```

---

## 🧑‍💻 Contacts

- Author: [yattorie](https://github.com/yattorie)

