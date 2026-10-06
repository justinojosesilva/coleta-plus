# Coleta+ ESG API

Spring Boot REST API for waste collection, monitoring, and ESG indicators. Oracle + Flyway + JWT-secured.

## Run locally

```bash
./mvnw spring-boot:run
```

Requires reachable Oracle (default `oracle.fiap.com.br:1521:ORCL`). Override via env vars: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`.

## Run with Docker

```bash
docker compose up --build
```

Brings up `oracle-db` (Oracle 23 Free) and the app on `http://localhost:8080`. Flyway applies V001–V008 on first start, including seed users:

| Email                        | Password      | Role          |
|------------------------------|---------------|---------------|
| admin@coletaplus.local       | `admin123`    | `ROLE_ADMIN`  |
| operador@coletaplus.local    | `operador123` | `ROLE_USER`   |
| auditor@coletaplus.local     | `auditor123`  | `ROLE_AUDIT`  |

## Auth flow

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"admin@coletaplus.local","password":"admin123"}' | jq -r .token)

curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/residues
```

## Swagger / OpenAPI

- UI: <http://localhost:8080/swagger-ui.html>
- Spec: <http://localhost:8080/v3/api-docs>

Click **Authorize** and paste the JWT to call secured endpoints.
