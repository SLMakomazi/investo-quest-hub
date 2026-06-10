# Enviro365 Investments - Withdrawal Notice System

eTalente Junior Full-Stack Assessment - Spring Boot + React

## Project Structure

enviro365-assessment/
- backend/      - Spring Boot 3 (Java 17), H2 in-memory DB
- frontend/     - React 18 + Vite
- database/     - Optional PostgreSQL schema + sample data
- README.md     - This file

## Prerequisites

- Java 17
- Maven
- Node.js and npm

## How to Run

### Backend

cd backend
mvn spring-boot:run

Backend runs on http://localhost:8080
H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:enviro)

### Frontend

cd frontend
npm install
npm run dev

Frontend runs on http://localhost:5173

## API Endpoints

- GET /api/investors/{id} - Investor details
- GET /api/portfolios/investor/{investorId} - Portfolio and products
- POST /api/withdrawals - Submit withdrawal notice
- GET /api/withdrawals/investor/{investorId} - Withdrawal history
- GET /api/withdrawals/export?investorId=1 - CSV statement download

## Business Rules

- Retirement withdrawals only allowed if investor age is greater than 65
- Withdrawal amount must not exceed product balance
- Withdrawal amount must not exceed 90% of product balance
- Proper error handling and user feedback implemented

## Advanced Requirements Implemented

1. Global exception handling - GlobalExceptionHandler with RestControllerAdvice
2. DTO layer - Request/response DTOs separate from JPA entities
3. Input validation - Jakarta Bean Validation annotations
4. Unit tests - WithdrawalValidatorTest with JUnit 5
5. UI validation - Client-side validation in WithdrawalForm component

## How to Change Investor Profiles

To switch between different investors in the frontend:

1. Open frontend/src/App.jsx
2. Change the CURRENT_INVESTOR_ID value (line 8)
3. Available investor IDs from data.sql:
   - ID 1: Thabo Mokoena (age 70) - Can withdraw from retirement products
   - ID 2: Aisha Patel (age 45) - Cannot withdraw from retirement products
   - ID 3: Sipho Dlamini (age 30) - Cannot withdraw from retirement products

To modify investor data in the backend:

1. Open backend/src/main/resources/data.sql
2. Edit the investor INSERT statements
3. Restart the backend to apply changes

## Database

The application uses H2 in-memory database by default (no setup required).

Optional PostgreSQL setup:
psql -U postgres -f database/schema.sql
psql -U postgres -d enviro -f database/sample_data.sql

To switch to PostgreSQL, update backend/src/main/resources/application.properties.

## AI Usage Disclosure

AI tools were used to scaffold boilerplate code including entity classes, controller wiring, and README structure. All business logic including validation rules, balance calculations, and exception handling was reviewed and fully understood. No proprietary data was shared with AI tools.

## Assessment Requirements Compliance

This project meets all eTalente assessment requirements:

Backend (Spring Boot - Mandatory):
- Investor portfolio retrieval with details and products
- Withdrawal notice creation with balance calculations
- CSV statement export with filtering

Frontend (Mandatory):
- Portfolio dashboard
- Withdrawal form
- Withdrawal history table
- CSV download button
- Full API integration

Business Rules:
- Age restriction for retirement withdrawals (age > 65)
- Balance validation
- 90% withdrawal limit
- Comprehensive error handling

Advanced Features (5 out of 5 implemented):
- Global exception handling
- DTO layer
- Input validation
- Unit tests
- UI validation
