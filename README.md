# Course Allocation Service (MVP)

Simple Spring Boot service that manages Students, Courses, and Allocations (enrollments).

Features:
- Create / Retrieve / Delete Students
- Create / Retrieve / Delete Courses
- Create / Delete Allocations (with capacity and duplicate checks)
- H2 in-memory DB and H2 console
- OpenAPI (Swagger UI) via springdoc

How to build & run
------------------

Requirements: JDK 17+, Maven

From project root (where `pom.xml` is):

```powershell
mvn spring-boot:run
```

Access:
- Swagger UI: http://localhost:8080/swagger-ui.html  (or /swagger-ui/index.html)
- H2 Console: http://localhost:8080/h2-console  (JDBC URL: `jdbc:h2:mem:course_db`, user: `sa`)

Notes
-----
- The application uses an in-memory H2 database (data not persisted across restarts).
- Business rules (capacity, duplicate allocations) are enforced in the service layer and return HTTP 409 on conflict.
