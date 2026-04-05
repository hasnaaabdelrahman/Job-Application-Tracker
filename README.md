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
| H2 Database | File-based embedded database (persistent storage) |
| JWT (JSON Web Token) | Stateless authentication |
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
├── config          # Security config, JWT utilities
├── exceptions      # Custom exception classes, GlobalExceptionHandler (@ControllerAdvice)
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

1. Register via `POST /api/auth/register` — account is created with `ROLE_USER`
2. Login via `POST /api/auth/login` — returns a JWT token
3. Include the token in subsequent requests as a Bearer token:

```
Authorization: Bearer <your_token>
```

### Endpoint Access Rules

| Endpoint | Access |
|---|---|
| `POST /api/auth/**` | Public (no token required) |
| `GET /api/user/v1/get-all` | `ROLE_ADMIN` only |
| `DELETE /api/user/v1/delete/{id}` | `ROLE_ADMIN` only |
| `GET /api/user/v1/getById/{id}` | Authenticated (`ROLE_USER` or `ROLE_ADMIN`) |
| `PUT /api/user/v1/update/{id}` | Authenticated (`ROLE_USER` or `ROLE_ADMIN`) |
| All other endpoints | Authenticated |

---

### Assigning Admin Role
 
Every new account registered via `/api/auth/register` gets `ROLE_USER` by default. To assign `ROLE_ADMIN`, use one of the following approaches:
 
**via H2 Console**
 
1. Make sure your `SecurityConfig` allows H2 console access:
```java
.requestMatchers("/h2-console/**").permitAll()
// and disable frame options:
.headers(headers -> headers.frameOptions(frame -> frame.disable()))
```
 
2. Open `http://localhost:8080/h2-console` with these credentials:
 
| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/testdb` |
| Username | `sa` |
| Password | *(leave empty)* |
 
3. Run:
```sql
SELECT * FROM USERS; -- find the user id first
INSERT INTO USER_ROLES (USER_ID, ROLES) VALUES (1, 'ROLE_ADMIN');
```

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

### Auth `api/auth` — Public

| Method | Endpoint | Description |
|---|---|---|
| POST | `/login` | Login and receive JWT token |
| POST | `/register` | Register a new user account |

### Applications `api/application/v1` — Authenticated

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all applications |
| GET | `/getByCompany/{id}` | Get applications by company |
| GET | `/getByStatus?status=` | Filter by status |
| GET | `/stats` | Get count grouped by status |
| POST | `/add` | Create new application |
| PUT | `/update/{id}` | Update application |
| DELETE | `/delete` | Delete application (REJECTED only) |

### Jobs `api/job/v1` — Authenticated

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all jobs |
| GET | `/getByCompany/{id}` | Get jobs by company |
| GET | `/search?title=` | Search jobs by title keyword |
| POST | `/add` | Create new job |
| PUT | `/update/{id}` | Update job |
| DELETE | `/delete/{id}` | Delete job |

### Companies `api/company/v1` — Authenticated

| Method | Endpoint | Description |
|---|---|---|
| GET | `/get-all` | Get all companies |
| GET | `/get/{id}` | Get company by ID |
| POST | `/add` | Create new company |
| PUT | `/update/{id}` | Update company |
| DELETE | `/delete/{id}` | Delete company |

### Users `api/user/v1` — Authenticated / Admin

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/getById/{id}` | Authenticated | Get user by ID |
| GET | `/get-all` | Admin only | Get all users |
| PUT | `/update/{id}` | Authenticated | Update user |
| DELETE | `/delete/{id}` | Admin only | Delete user |

---

## Sample Requests & Responses

### POST `/api/auth/register`

**Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "roles": ["USER"]
}
```

### POST `/api/auth/login`

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

After running the application, access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

> To test protected endpoints in Swagger, click **Authorize** and enter your JWT token as: `Bearer <token>`

---

## Configuration

Configure credentials in `application.properties`:

```properties
# File-based H2 database (data persists across restarts)
spring.datasource.url=jdbc:h2:file:./data/testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

# JWT secret key
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
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

- Pagination for list endpoints
- User can only update/delete their own applications (ownership check)
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
