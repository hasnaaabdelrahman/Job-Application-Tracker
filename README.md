#  Job Application Tracker API

A backend RESTful API built with Spring Boot to help users track and manage their job applications efficiently.

---

##  Overview

Job Application Tracker is a system that allows users to:

- Manage job applications in one place  
- Track application status (Applied, Interview, Rejected, Accepted)  
- Organize jobs by companies  

This project demonstrates clean backend architecture using modern development practices.

---

## Tech Stack

- Java  
- Spring Boot  
- Spring Data JPA  
- H2 Database (for development & testing)  
- Maven  

---

##  Project Structure

```
src/main/java/com/example/jobtracker

├── controller     # REST Controllers
├── service        # Business logic
├── repository     # Data access layer
├── entity         # JPA Entities
├── dto            # Data Transfer Objects
```

---

##  Entities & Relationships

###  User
- id
- name
- email
- phone
- birthdate  

 One user can have multiple applications  

---

###  Company
- id
- name  

 One company can have multiple jobs  

---

### Job
- id
- title
- description  

 Each job belongs to one company  
 One job can have multiple applications  

---

### Application
- id
- status (APPLIED, INTERVIEW, REJECTED, ACCEPTED)  

 Each application belongs to:
- one user  
- one job  

---

##  Relationships Summary

- User → Applications (One-to-Many)  
- Company → Jobs (One-to-Many)  
- Job → Applications (One-to-Many)  
- Application → User & Job (Many-to-One)  

---

##  Features

- CRUD operations for all entities  
- Track application status using Enum  
- Filter applications by status or company  
---

---

## API Documentation (Swagger)

The project includes integrated API documentation using OpenAPI (Swagger UI).

After running the application, you can access the documentation at:

http://localhost:8080/swagger-ui/index.html

Swagger allows you to:
- View all available endpoints  
- Test APIs directly from the browser  
- Inspect request and response structures  

---


##  Configuration

### H2 Database

The project uses an in-memory database for easy setup.

Access H2 Console:
```
http://localhost:8080/h2-console
```

Example configuration in `application.properties`:

```
spring.datasource.username=root
spring.datasource.password=password
spring.h2.console.enabled=true
```

---

##  Running the Project

1. Clone the repository  
2. Open in IntelliJ / VS Code  
3. Run the application  

```
mvn spring-boot:run
```

---

##  Sample Endpoints

###  Applications
- GET /applications  
- POST /applications  
- GET /applications/{id}  
- DELETE /applications/{id}  

---

###  Jobs
- GET /jobs  
- POST /jobs  

---

###  Companies
- GET /companies  
- POST /companies  

---

###  Users
- GET /users  
- POST /users
- DELETE /users
- UPDATE /users

---

## Future Improvements

-  Add authentication (JWT)  


---

##  Purpose

This project was built to practice:

- Spring Boot & REST API development  
- JPA relationships and database design  
- Clean architecture principles  

