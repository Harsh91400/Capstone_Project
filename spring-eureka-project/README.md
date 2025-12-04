# Spring Eureka Project (Eureka Server + App Service)

This archive contains two Spring Boot projects:

1. **eureka-server** (port 8761) — Eureka discovery server.
2. **app-service** (port 8081) — A sample service (registered to Eureka) implementing:
   - Add User (`POST /users/add`)
   - Login User (`POST /users/login`)
   - Add Admin (`POST /admins/add`)
   - Login Admin (`POST /admins/login`)
   - Add App (by Admin) (`POST /apps/add`)
   - Show App (`GET /apps`)
   - Show App By Genre (`GET /apps/genre/{genre}`)

Database: H2 (in-memory) for `app-service`.

To run:
- Start eureka-server (port 8761).
- Start app-service (port 8081).
- Use Postman or curl to call endpoints.

