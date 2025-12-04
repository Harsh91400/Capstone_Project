# app-service

A simple Spring Boot service (Eureka client) exposing REST endpoints for admin, user and app operations.

Endpoints:
- POST /admins/add
- POST /admins/login
- POST /users/add
- POST /users/login
- POST /apps/add
- GET /apps
- GET /apps/genre/{genre}

H2 console: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:appdb
