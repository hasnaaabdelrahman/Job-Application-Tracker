# Job Application Tracker API

A backend RESTful API built with Spring Boot to help users track and manage their job applications efficiently.

---

## Overview

Job Application Tracker is a system that allows users to:
- Manage job applications in one place
- Track application status (Applied, Interview, Rejected, Accepted)
- Organize jobs by companies
- Filter jobs by title, location, type, and minimum salary
- Search jobs by title keyword
- View the most recently posted jobs
- Upload a resume (PDF) to their profile
- View a personal dashboard of application statistics
- View application statistics grouped by status
- (Admin) View job-type and company-level dashboard stats

This project demonstrates clean backend architecture using modern development practices.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 4 | Application framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Data access layer |
| PostgreSQL | Relational database |
| JWT (JSON Web Token) | Stateless authentication |
| Maven | Build tool |
| Swagger / OpenAPI | API documentation |
| JUnit 5 | Unit & integration testing framework |
| Mockito | Mocking framework for service unit tests |
| H2 Database | In-memory database for repository integration tests |
| Docker / Docker Compose | Containerized local setup (API + PostgreSQL) |

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
2. Login via `POST /api/v1/auth/login` — returns an `accessToken` and a `refreshToken`
3. Include the access token in subsequent requests as a Bearer token:

```
Authorization: Bearer <your_access_token>
```

4. When the access token expires, call `POST /api/v1/auth/refresh-token` with the refresh token to get a new one without logging in again

### Endpoint Access Rules

| Endpoint | Access |
|---|---|
| `POST /api/v1/auth/register` | Public (no token required) |
| `POST /api/v1/auth/login` | Public (no token required) |
| `POST /api/v1/auth/refresh-token` | Public (requires a valid refresh token) |
| `PUT /api/v1/auth/rest-password` | Authenticated |
| `POST /api/v1/auth/logout` | Authenticated |
| `GET /api/v1/user/users` | `ROLE_ADMIN` only |
| `DELETE /api/v1/user/delete/{id}` | `ROLE_ADMIN` only |
| `GET /api/v1/user/get/me` | Authenticated (`ROLE_USER` or `ROLE_ADMIN`) — returns the current user |
| `PUT /api/v1/user/update/{id}` | Authenticated — own profile only |
| `GET /api/v1/user/dashboard` | `ROLE_USER` only |
| `POST /api/v1/user/upload-resume` | `ROLE_USER` only |
| `GET /api/v1/job/stats` | `ROLE_ADMIN` only |
| `GET /api/company/v1/dashboard/{id}` | `ROLE_ADMIN` only |
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

### Auth `/api/v1/auth`

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/register` | Public | Register a new user account |
| POST | `/login` | Public | Login and receive an access + refresh token pair |
| POST | `/refresh-token` | Public | Exchange a valid refresh token for a new access token |
| PUT | `/rest-password` | Authenticated | Change the current user's password |
| POST | `/logout` | Authenticated | Invalidate the current token |

### Applications `/api/v1/application` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get/{id}` | USER / ADMIN | Get application by ID — admin can view any; a user can only view their own |
| GET | `/get` | USER / ADMIN | Get all applications (paginated) — admin sees all, user sees only their own |
| GET | `/companies/{id}/applications` | USER / ADMIN | Get applications for a company — admin sees all, user sees only their own |
| GET | `/search?status=` | USER / ADMIN | Filter applications by status — admin sees all, user sees only their own |
| GET | `/users/{id}` | USER / ADMIN | Get applications for a given user ID |
| GET | `/stats` | USER / ADMIN | Get application counts grouped by status — admin gets global stats, user gets their own |
| POST | `/jobs/{id}/apply` | USER | Apply to a job — creates an application for the current user |
| PUT | `/update/{id}` | ADMIN | Update an application |
| DELETE | `/delete/{id}` | USER | Delete an application |
| GET | `/jobs/{id}/applications/count` | ADMIN | Count applications received for a job |
| DELETE | `/{id}/withdraw` | USER | Withdraw the current user's own application |

### Jobs `/api/v1/job` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get/{id}` | USER / ADMIN | Get job by ID |
| GET | `/get` | USER / ADMIN | Get all jobs (paginated) |
| GET | `/companies/{id}/jobs` | USER / ADMIN | Get jobs by company (paginated) |
| GET | `/search/{title}` | USER / ADMIN | Search jobs by title keyword |
| GET | `/filter` | USER / ADMIN | Filter jobs by optional `title`, `location`, `type`, `minSalary` query params |
| GET | `/stats` | ADMIN | Get job counts grouped by job type |
| POST | `/add` | ADMIN | Create new job |
| PUT | `/update/{id}` | ADMIN | Update job |
| DELETE | `/delete/{id}` | ADMIN | Delete job |
| GET | `/lastest` | USER / ADMIN | Get the most recently posted jobs |

### Companies `/api/company/v1` — Authenticated

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get` | USER / ADMIN | Get all companies (paginated) |
| GET | `/get/{id}` | USER / ADMIN | Get company by ID |
| POST | `/add` | ADMIN | Create new company |
| PUT | `/update/{id}` | ADMIN | Update company |
| DELETE | `/delete/{id}` | ADMIN | Delete company |
| GET | `/dashboard/{id}` | ADMIN | Get dashboard stats for a company |

### Users `/api/v1/user` — Authenticated / Admin

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/get/me` | USER / ADMIN | Get the currently authenticated user |
| GET | `/users` | ADMIN only | Get all users (paginated, sortable) |
| PUT | `/update/{id}` | Own account only | Update user profile |
| DELETE | `/delete/{id}` | ADMIN only | Delete user |
| GET | `/dashboard` | USER only | Get the current user's application stats |
| GET | `/profile` | USER / ADMIN | Get the current user's profile info |
| POST | `/upload-resume` | USER only | Upload a resume (PDF only, multipart form) |

> **Note:** admin-vs-user scoping on the Applications endpoints is currently done by checking whether the authenticated username is `admin@gmail.com`, rather than by role alone — worth keeping in mind if the default admin email is ever changed.

> **Known issue:** `POST /api/v1/user/upload-resume` checks the uploaded file's content type against the literal string `"applicaion/pdf"` (typo — missing the `t` in "application"). As written, this comparison never matches a real PDF's `application/pdf` content type, so every upload is currently rejected with `"Only PDF files are allowed"` regardless of the file. Fix pending.

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
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### POST `/api/v1/application/jobs/{id}/apply`

Applies the current authenticated user to the job with the given `{id}`. No request body is needed — the user and job are both resolved from the authenticated principal and the path variable.

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

`application.properties` reads its values from environment variables (via `spring.config.import=optional:file:.env[.properties]`), so configuration is supplied through a `.env` file in the project root rather than by editing `application.properties` directly.

Create a `.env` file in the project root with the following variables:

```properties
# PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/job_tracker
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_jwt_secret_key
JWT_ACCESS=300000
JWT_REFRESHMENT_DAYS=7
```

> `.env` is excluded from version control via `.gitignore` to protect credentials.

### Database Setup

```sql
CREATE DATABASE job_tracker;
```

---

## Running the Project

### Option 1: Run locally with Maven

1. Clone the repository
```bash
git clone https://github.com/hasnaaabdelrahman/Job-Application-Tracker.git
```

2. Navigate to the project directory
```bash
cd Job-Application-Tracker
```

3. Set up configuration and the database as described in [Configuration](#configuration)

4. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Option 2: Run with Docker Compose

The project ships with a `Dockerfile` and `docker-compose.yaml` that run the API alongside a PostgreSQL container.

1. Build the application JAR (Docker Compose expects `target/*.jar` to already exist)
```bash
mvn clean package -DskipTests
```

2. Start the containers
```bash
docker compose up --build
```

This starts:
- `app` — the Spring Boot API on `http://localhost:8080`
- `db` — a PostgreSQL 16 container on `localhost:5432` (database `job_tracker`, user/password `postgres`/`postgres`)

> The default Compose credentials in `docker-compose.yaml` are for local development only — change them before using this setup anywhere else.

3. Stop the containers
```bash
docker compose down
```

---

## Future Improvements

- Integration tests for controllers
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