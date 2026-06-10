# Enviro365 Investments — Withdrawal Notice System

Junior Full-Stack Assessment (Spring Boot + React).

```
enviro365-assessment/
├── backend/      # Spring Boot 3 (Java 17), H2 in-memory DB
├── frontend/     # React 18 + Vite (plain JS, per-component CSS)
├── database/     # Optional PostgreSQL schema + sample data
└── README.md
```

## 1. Backend (Spring Boot, MANDATORY)

Package: `com.enviro.assessment.junior.yourname`
Runtime DB: **H2 in-memory** (as required by the brief). Seeded on startup via `data.sql`.

```bash
cd backend
./mvnw spring-boot:run        # or: mvn spring-boot:run
# API:        http://localhost:8080
# H2 console: http://localhost:8080/h2-console  (jdbc:h2:mem:enviro)
```

### REST endpoints

| Method | URL                                         | Purpose                       |
|--------|---------------------------------------------|-------------------------------|
| GET    | `/api/investors/{id}`                       | Investor details              |
| GET    | `/api/portfolios/investor/{investorId}`     | Portfolio + products          |
| POST   | `/api/withdrawals`                          | Submit a withdrawal notice    |
| GET    | `/api/withdrawals/investor/{investorId}`    | Withdrawal history            |
| GET    | `/api/withdrawals/export?investorId=1`      | CSV statement download        |

### Business rules (enforced in `WithdrawalValidator`)
- Retirement product withdrawals require age **> 65**.
- Withdrawal must **not exceed** the product balance.
- Withdrawal must **not exceed 90%** of the product balance.
- Validation errors return structured JSON via `GlobalExceptionHandler`.

### Advanced requirements implemented
1. **Global exception handling** — `@RestControllerAdvice`
2. **DTO layer** — request/response DTOs separate from JPA entities
3. **Input validation** — Jakarta Bean Validation (`@NotNull`, `@Positive`)
4. **Unit tests** — `WithdrawalValidatorTest`
5. **UI validation** — client-side checks in `WithdrawalForm`

## 2. Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev      # http://localhost:5173
```

Pages: Dashboard, Withdrawals (form), History (table + CSV download).
API base URL configured in `src/api/axiosClient.js`.

## 3. Database (optional PostgreSQL sample data)

The grader runs on H2 (zero-config). The `database/` folder contains an
equivalent PostgreSQL schema + seed data if you want to run against Postgres:

```bash
psql -U postgres -f database/schema.sql
psql -U postgres -d enviro -f database/sample_data.sql
```

To switch the backend to Postgres, update `backend/src/main/resources/application.properties`.

## 4. AI Usage Disclosure

AI assistance (Lovable / ChatGPT) was used to scaffold boilerplate (entity classes,
controller wiring, README structure). All business logic — validation rules,
balance calculations, exception hierarchy — was reviewed line-by-line and is
fully understood by the author. No proprietary data was shared with AI tools.

## 5. Screenshots
Add screenshots of Dashboard, Withdrawal form, and History page to `/docs/screenshots/`.
