# Course Allocation Service (MVP)

A minimal, stateless, RESTful Spring Boot service for managing students, courses, and their allocations (enrollments). This MVP enforces core business rules: enrollment capacity limits and duplicate enrollment prevention.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database](#database)
- [Testing](#testing)
- [Architecture](#architecture)
- [Business Rules](#business-rules)
- [Error Handling](#error-handling)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Student Management**: Create, retrieve (single/all), and delete students.
- **Course Management**: Create, retrieve (single/all), and delete courses.
- **Allocation Management**: Allocate/deallocate students to courses, with listings of courses per student and students per course.
- **Business Rule Enforcement**: Automatic rejection of allocations exceeding course capacity or duplicating existing enrollments.
- **In-Memory Database**: H2 database for development/testing (data resets on restart).
- **API Documentation**: Self-documented via SpringDoc OpenAPI (Swagger UI).
- **Error Handling**: Standardized HTTP status codes (400, 404, 409, 500) with JSON error responses.
- **3-Layer Architecture**: Clean separation of controllers, services, and repositories.

## Project Structure
```
course-selection-server/
├── pom.xml                          # Maven configuration with Spring Boot dependencies
├── README.md                        # This file
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java            # Spring Boot application entry point
│   │   │   ├── controller/          # REST controllers
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   └── AllocationController.java
│   │   │   ├── service/             # Business logic services
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── CourseService.java
│   │   │   │   └── AllocationService.java
│   │   │   ├── repository/          # Data access layer (JPA repositories)
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── AllocationRepository.java
│   │   │   ├── model/               # JPA entities
│   │   │   │   ├── Student.java
│   │   │   │   ├── Course.java
│   │   │   │   └── Allocation.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── StudentDTO.java
│   │   │   │   ├── CourseDTO.java
│   │   │   │   └── AllocationRequestDTO.java
│   │   │   └── exception/           # Custom exceptions and global handler
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── ConflictException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties  # Spring Boot configuration (H2, JPA)
│   └── test/
│       └── java/org/example/service/
│           └── AllocationServiceIntegrationTest.java  # Integration tests
└── target/                          # Build output (generated)
```

## Prerequisites
- **Java**: JDK 17 or higher (e.g., Eclipse Temurin 17).
- **Maven**: 3.6+ (or use Maven Wrapper if added).
- **Operating System**: Windows, macOS, or Linux.

## Installation & Setup
1. **Clone or Download**: Ensure the project is in a local directory, e.g., `C:\Users\naeemaziz\Desktop\draka labs\course-selection-system\course-selection-server`.

2. **Verify Java and Maven**:
   ```powershell
   java -version  # Should show Java 17+
   mvn -v         # Should show Maven 3.6+
   ```

3. **Build the Project**:
   ```powershell
   cd 'C:\Users\naeemaziz\Desktop\draka labs\course-selection-system\course-selection-server'
   mvn clean compile
   ```

## Running the Application
1. **Start the Server**:
   ```powershell
   mvn spring-boot:run
   ```
   - Default port: 8080.
   - To use a different port: `mvn spring-boot:run -Dserver.port=8081`.

2. **Access Points**:
   - **Swagger UI**: http://localhost:8080/swagger-ui.html (interactive API docs).
   - **H2 Console**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:course_db`, user: `sa`, no password).
   - **Application Base URL**: http://localhost:8080/api/

3. **Stop the Server**: Press `Ctrl+C` in the terminal.

## API Documentation
The API is RESTful, stateless, and uses JSON payloads. All endpoints are prefixed with `/api/`.

### Students
- **POST** `/api/students` - Create a student.
  - Body: `{"firstName": "John", "lastName": "Doe", "email": "john.doe@example.com"}`
  - Response: 201 Created with student details.
- **GET** `/api/students` - List all students.
- **GET** `/api/students/{id}` - Get student by ID.
- **DELETE** `/api/students/{id}` - Delete student by ID (204 No Content).

### Courses
- **POST** `/api/courses` - Create a course.
  - Body: `{"title": "Introduction to Programming", "courseCode": "CS101", "capacity": 30}`
  - Response: 201 Created with course details.
- **GET** `/api/courses` - List all courses.
- **GET** `/api/courses/{id}` - Get course by ID.
- **DELETE** `/api/courses/{id}` - Delete course by ID (204 No Content).

### Allocations
- **POST** `/api/allocations` - Allocate a student to a course.
  - Body: `{"studentId": 1, "courseId": 1}`
  - Response: 201 Created.
- **DELETE** `/api/allocations/{id}` - Deallocate by allocation ID (204 No Content).
- **GET** `/api/students/{id}/courses` - List courses for a student.
- **GET** `/api/courses/{id}/students` - List students for a course.

### Error Responses
- **400 Bad Request**: Validation errors (e.g., missing fields).
- **404 Not Found**: Resource not found.
- **409 Conflict**: Business rule violation (capacity exceeded or duplicate allocation).
- **500 Internal Server Error**: Unexpected errors.

Example Error Response:
```json
{
  "error": "Course capacity reached"
}
```

## Database
- **Type**: H2 In-Memory Database.
- **Persistence**: Data is not saved across application restarts (ideal for development/testing).
- **Console Access**: Visit http://localhost:8080/h2-console.
  - JDBC URL: `jdbc:h2:mem:course_db`
  - Username: `sa`
  - Password: (leave blank)
- **Tables**: Automatically created via JPA/Hibernate on startup.

## Testing
- **Run Tests**: `mvn test`
- **Coverage**: Integration tests for allocation business rules (capacity and duplicate checks).
- **Test File**: `src/test/java/org/example/service/AllocationServiceIntegrationTest.java`

## Architecture
- **3-Layer Architecture**:
  - **Controller Layer**: Handles HTTP requests/responses, delegates to services.
  - **Service Layer**: Contains business logic, enforces rules, interacts with repositories.
  - **Repository Layer**: Data access via Spring Data JPA.
- **Entities**: JPA-annotated classes with relationships and constraints.
- **DTOs**: Decouple API contracts from entities.
- **Exception Handling**: Global `@ControllerAdvice` for consistent error responses.

## Business Rules
- **C-1: Capacity Enforcement**: Allocations are rejected if the course's current enrollment equals or exceeds its capacity.
- **C-2: Duplicate Prevention**: A student cannot be allocated to the same course twice (enforced via unique constraint on `student_id` + `course_id`).

## Error Handling
- Custom exceptions (`ResourceNotFoundException`, `ConflictException`) are thrown in services.
- `GlobalExceptionHandler` maps them to appropriate HTTP statuses with JSON error messages.
- Validation errors (e.g., from `@Valid`) are also handled.

## Contributing
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit changes: `git commit -am 'Add your feature'`.
4. Push to branch: `git push origin feature/your-feature`.
5. Submit a pull request.

## License
This project is for educational purposes. No specific license applied.
