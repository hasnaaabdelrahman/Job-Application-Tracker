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
| Spring Data JPA | Data access layer |
| H2 Database | In-memory database (dev & testing) |
| Maven | Build tool |
| Swagger / OpenAPI | API documentation |

---

## Prerequisites

- Java 17+
- Maven 3.8+

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
```

---

## Entities & Relationships

### User
| Field | Type |
|---|---|
| id | Integer |
| name | String |
| email | String |
| phone | String |
| birthDate | LocalDate |

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
User ──────< Application >────── Job ──────< Company
```

- User → Applications (One-to-Many)
- Company → Jobs (One-to-Many)
- Job → Applications (One-to-Many)
- Application → User & Job (Many-to-One)

---

## API Endpoints

### Applications `api/application/v1`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all applications |
| GET | `/getByCompany/{id}` | Get applications by company |
| GET | `/getByStatus?status=` | Filter by status |
| GET | `/stats` | Get count grouped by status |
| POST | `/add` | Create new application |
| PUT | `/update/{id}` | Update application |
| DELETE | `/delete` | Delete application (REJECTED only) |

### Jobs `api/job/v1`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all jobs |
| GET | `/getByCompany/{id}` | Get jobs by company |
| GET | `/search?title=` | Search jobs by title keyword |
| POST | `/add` | Create new job |
| PUT | `/update/{id}` | Update job |
| DELETE | `/delete/{id}` | Delete job |

### Companies `api/company/v1`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all companies |
| GET | `/get/{id}` | Get company by ID |
| POST | `/add` | Create new company |
| PUT | `/update/{id}` | Update company |
| DELETE | `/delete/{id}` | Delete company |

### Users `api/user/v1`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all users |
| POST | `/add` | Create new user |
| PUT | `/update/{id}` | Update user |
| DELETE | `/delete/{id}` | Delete user |

---

## Sample Request & Response

### POST `/api/application/v1/add`

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

### GET `/api/application/v1/stats`

**Response:**
```json
{
  "APPLIED": 5,
  "INTERVIEW": 3,
  "REJECTED": 2,
  "ACCEPTED": 1
}
```

### GET `/api/job/v1/search?title=engineer`

**Response:**
```json
[
  {
    "id": 1,
    "title": "Backend Engineer",
    "description": "Java Spring Boot role",
    "company_id": 2
  }
]
```

---

## API Documentation (Swagger)

The project includes integrated API documentation using OpenAPI (Swagger UI).

After running the application, access the documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Configuration

Configure credentials in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

Access H2 Console at:
```
http://localhost:8080/h2-console
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

3. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## Future Improvements

- Add authentication & authorization (JWT + Spring Security)
- Global exception handling with meaningful HTTP responses
- Pagination for list endpoints
- Deploy to cloud (Railway / Render)

---

## Purpose

This project was built to practice:
- Spring Boot & REST API development
- JPA relationships and database design
- Clean architecture principles (Controller → Service → Mapper → Repository)
- DTO pattern for decoupling API layer from persistence layer
