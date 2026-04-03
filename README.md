# Job Application Tracker API

A backend RESTful API built with Spring Boot to help users track and manage their job applications efficiently.

---

## Overview

Job Application Tracker allows users to:
- Manage job applications in one place
- Track application status (Applied, Interview, Rejected, Accepted)
- Organize jobs by companies
- View application statistics grouped by status

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3 | Application framework |
| Spring Data JPA | Data access layer |
| H2 Database | In-memory database for development |
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
├── controller      # REST Controllers (request handling)
├── service         # Business logic
├── repository      # Data access layer (JPA Repositories)
├── entity          # JPA Entities
├── dto             # Data Transfer Objects
├── mapper          # Entity <-> DTO mapping
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
| status | Enum (APPLIED, INTERVIEW, REJECTED, ACCEPTED) |
| user | User |
| job | Job |

### Relationships Summary
```
User         ──< Application
Company      ──< Job
Job          ──< Application
Application >── User
Application >── Job
```

---

## API Endpoints

### Users — `/api/user/v1`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all users |
| POST | `/add` | Create a new user |
| PUT | `/update/{id}` | Update a user |
| DELETE | `/delete/{id}` | Delete a user |

### Companies — `/api/company/v1`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all companies |
| POST | `/add` | Create a new company |

### Jobs — `/api/job/v1`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all jobs |
| POST | `/add` | Create a new job |
| PUT | `/update/{id}` | Update a job |
| DELETE | `/delete/{id}` | Delete a job |

### Applications — `/api/application/v1`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all applications |
| GET | `/getByCompany/{id}` | Get applications by company |
| GET | `/getByStatus?status=APPLIED` | Filter applications by status |
| GET | `/stats` | Get application count grouped by status |
| POST | `/add` | Create a new application |
| PUT | `/update/{id}` | Update an application |
| DELETE | `/delete` | Delete a rejected application |

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

---

## API Documentation (Swagger)

After running the application, access the full interactive API docs at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Configuration

Configure the following in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

Access the H2 console at:
```
http://localhost:8080/h2-console
```

---

## Running the Project

```bash
# 1. Clone the repository
git clone https://github.com/your-username/job-application-tracker.git

# 2. Navigate to the project directory
cd job-application-tracker

# 3. Run the application
mvn spring-boot:run
```

---

## Future Improvements

- JWT Authentication & Authorization
- Global exception handling with meaningful error responses
- Filter jobs by title keyword
- Pagination for large result sets
- PostgreSQL support for production deployment

---

## Purpose

This project was built to practice:
- Spring Boot & REST API development
- JPA relationships and database design
- Clean layered architecture (Controller → Service → Repository)
- DTO pattern and entity mapping
