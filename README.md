# Job Application Tracker API

A backend RESTful API built with Spring Boot to help users track and manage their job applications efficiently.

---

## Overview

Job Application Tracker is a system that allows users to:
- Manage job applications in one place
- Track application status (Applied, Interview, Rejected, Accepted)
- Organize jobs by companies
- Filter applications by status or company
- Search jobs by title keyword
- View application statistics grouped by status

This project demonstrates clean backend architecture using modern development practices.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot | Application framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Data access layer |
| PostgreSQL | Relational database |
| JWT (JSON Web Token) | Stateless authentication |
| Maven | Build tool |
| Swagger / OpenAPI | API documentation |
| JUnit 5 | Unit & integration testing framework |
| Mockito | Mocking framework for service unit tests |
| H2 Database | In-memory database for repository integration tests |

---

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

---

## Project Structure

```
src/main/java/com/job/application/tracker
├── controller      # REST Controllers
├── service         # Business logic
├── repository      # Data access layer
├── entity          # JPA Entities
├── dto             # Data Transfer Objects
├── mapper          # Entity <-> DTO mappers
├── config          # Security config, JWT utilities, DataSeeder
├── exceptions      # Custom exception classes, GlobalExceptionHandler (@ControllerAdvice)

src/test/java/com/job/application/tracker
├── service         # Unit tests for service layer (Mockito)
│   ├── ApplicationServiceTest.java
│   ├── CompanyServiceTest.java
│   ├── JobServiceTest.java
│   └── UserServiceTest.java
├── repository      # Integration tests for repository layer (@DataJpaTest / H2)
│   ├── CompanyRepositoryTest.java
│   ├── JobRepositoryTest.java
│   └── UserRepositoryTest.java
```

---

## Authentication & Authorization

This project uses **JWT-based stateless authentication** with **role-based access control (RBAC)**.

### Roles

| Role | Description |
|---|---|
| `ROLE_USER` | Regular user — can manage their own applications and profile |
| `ROLE_ADMIN` | Administrator — full access including user management |

### How It Works

1. Register via `POST /api/v1/auth/register` — account is created with `ROLE_USER`
2. Login via `POST /api/v1/auth/login` — returns a JWT token
3. Include the token in subsequent requests as a Bearer token:

```
Authorization: Bearer <your_token>
```

### Endpoint Access Rules

| Endpoint | Access |
|---|---|
| `POST /api/v1/auth/**` | Public (no token required) |
| `GET /api/v1/user/users` | `ROLE_ADMIN` only |
| `DELETE /api/v1/user/delete/{id}` | `ROLE_ADMIN` only |
| `GET /api/v1/user/get/{id}` | Authenticated (`ROLE_USER` or `ROLE_ADMIN`) |
| `PUT /api/v1/user/update/{id}` | Authenticated — own profile only |
| All other endpoints | Authenticated |

### Default Admin Account

A default admin user is automatically created on startup via `DataSeeder` if one doesn't already exist:

| Field | Value |
|---|---|
| Email | `admin@gmail.com` |
| Password | `admin123` |

> **Important:** Change these credentials before deploying to any environment.

Login with the admin credentials to receive a token with `ROLE_ADMIN`, which grants full access to all endpoints.

---

## Entities & Relationships

### User
| Field | Type |
|---|---|
| id | Integer |
| name | String |
| email | String |
| password | String (hashed) |
| phone | String |
| birthDate | LocalDate |
| roles | Set\<String\> |

### Company
| Field | Type |
|---|---|
| id | Integer |
| name | String |

### Job
| Field | Type |
|---|---|
| id | Integer |
| title | String |
| description | String |
| company | Company |

### Application
| Field | Type |
|---|---|
| id | Integer |
| status | APPLIED / INTERVIEW / REJECTED / ACCEPTED |
| user | User |
| job | Job |

### Relationships Summary

```
User ──────< Application >────── Job >────── Company
```

- User → Applications (One-to-Many)
- Company → Jobs (One-to-Many)
- Job → Applications (One-to-Many)
- Application → User & Job (Many-to-One)

---

## API Endpoints

### Auth `/api/v1/auth` — Public

| Method | Endpoint | Description |
|---|---|---|
| POST | `/login` | Login and receive JWT token |
| POST | `/register` | Register a new user account |

### Applications `/api/v1/application` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get` | USER / ADMIN | Get all applications (paginated) |
| GET | `/get/{id}` | USER / ADMIN | Get application by ID |
| GET | `/companies/{id}/applications` | USER / ADMIN | Get applications by company |
| GET | `/search?status=` | USER / ADMIN | Filter by status |
| GET | `/users/{id}` | USER / ADMIN | Get applications by user |
| GET | `/stats` | USER / ADMIN | Get count grouped by status |
| POST | `/add` | USER | Create new application |
| PUT | `/update/{id}` | ADMIN | Update application |
| DELETE | `/delete/{id}` | USER | Delete application |

### Jobs `/api/v1/job` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get` | USER / ADMIN | Get all jobs (paginated) |
| GET | `/get/{id}` | USER / ADMIN | Get job by ID |
| GET | `/companies/{id}/jobs` | USER / ADMIN | Get jobs by company |
| GET | `/search/{title}` | USER / ADMIN | Search jobs by title keyword |
| POST | `/add` | ADMIN | Create new job |
| PUT | `/update/{id}` | ADMIN | Update job |
| DELETE | `/delete/{id}` | ADMIN | Delete job |

### Companies `/api/company/v1` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get` | USER / ADMIN | Get all companies (paginated) |
| GET | `/get/{id}` | USER / ADMIN | Get company by ID |
| POST | `/add` | ADMIN | Create new company |
| PUT | `/update/{id}` | ADMIN | Update company |
| DELETE | `/delete/{id}` | ADMIN | Delete company |

### Users `/api/v1/user` — Authenticated / Admin

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get/{id}` | USER / ADMIN | Get user by ID |
| GET | `/users` | ADMIN only | Get all users (paginated) |
| PUT | `/update/{id}` | Own account only | Update user profile |
| DELETE | `/delete/{id}` | ADMIN only | Delete user |

---

## Sample Requests & Responses

### POST `/api/v1/auth/register`

**Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123",
  "phone": "01012345678",
  "birthDate": "1995-06-15"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

### POST `/api/v1/auth/login`

**Request:**
```json
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**
```
eyJhbGciOiJIUzI1NiJ9...
```

### POST `/api/v1/application/add`

**Request:**
```json
{
  "user_id": 1,
  "job_id": 2,
  "applicationStatus": "APPLIED"
}
```

**Response:**
```json
{
  "id": 5,
  "applicationStatus": "APPLIED"
}
```

### GET `/api/v1/application/stats`

**Response:**
```json
{
  "APPLIED": 5,
  "INTERVIEW": 3,
  "REJECTED": 2,
  "ACCEPTED": 1
}
```

### GET `/api/v1/job/search/engineer`

**Response:**
```json
[
  {
    "id": 1,
    "title": "Backend Engineer",
    "description": "Java Spring Boot role"
  }
]
```

---

## Tests

The project includes two levels of testing: **unit tests** for the service layer and **repository integration tests** using an in-memory H2 database.

### Service Tests — Unit Tests (Mockito)

Dependencies are mocked with Mockito. No database or Spring context is needed.

| Test Class | Test Cases |
|---|---|
| `ApplicationServiceTest` | Delete calls `deleteById` when found; throws `ResourceNotFoundException` when not found |
| `CompanyServiceTest` | Get returns company when found; throws exception when not found; delete calls `deleteById` when found; throws exception when not found |
| `JobServiceTest` | Get returns job when found; throws exception when not found; delete calls `deleteById` when found; throws exception when not found |
| `UserServiceTest` | Get returns user when found; throws exception when not found; delete throws exception when not found; delete calls `existsById` when found |

### Repository Tests — Integration Tests (@DataJpaTest + H2)

Run against a real in-memory H2 database. Spring Data JPA context is loaded for each test.

| Test Class | Test Cases |
|---|---|
| `CompanyRepositoryTest` | `existsById` returns true for saved company; returns false for missing ID |
| `JobRepositoryTest` | `findByCompanyId` returns jobs for valid company; returns empty for unknown company; `existsById` returns true/false correctly |
| `UserRepositoryTest` | `existsById`, `existsByEmail`, `existsByPhone` return true/false correctly; `existsByEmailAndIdNot` and `existsByPhoneAndIdNot` handle same/different user cases |

### Running Tests

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=ApplicationServiceTest
mvn test -Dtest=UserRepositoryTest

# Generate coverage report
mvn verify
# Report available at: target/site/jacoco/index.html
```

---

## API Documentation (Swagger)

After running the application, access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

> To test protected endpoints in Swagger, click **Authorize** and enter your JWT token as: `Bearer <token>`

---

## Configuration

This project uses environment-specific configuration. A template file is provided — copy it and fill in your values:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/job_tracker
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

> `application.properties` is excluded from version control via `.gitignore` to protect credentials.

### Database Setup

```sql
CREATE DATABASE job_tracker;
```

---

## Running the Project

1. Clone the repository
```bash
git clone https://github.com/your-username/job-application-tracker.git
```

2. Navigate to the project directory
```bash
cd job-application-tracker
```

3. Set up configuration
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# then edit application.properties with your DB credentials
```

4. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## Future Improvements

- Integration tests for controllers
- Refresh token support
- Deploy to cloud (Railway / Render)

---

## Purpose

This project was built to practice:
- Spring Boot & REST API development
- Spring Security with JWT authentication
- Role-based access control (RBAC)
- JPA relationships and database design
- Clean architecture principles (Controller → Service → Mapper → Repository)
- DTO pattern for decoupling API layer from persistence layer
- Unit testing with JUnit 5 and Mockito
- Repository integration testing with @DataJpaTest and H2
