# Enviro365 Investments - Withdrawal Notice System

eTalente Junior Full-Stack Assessment - Spring Boot + React

## Project Structure

investo-quest-hub/
- backend/      - Spring Boot 3 (Java 17), H2 in-memory DB
- frontend/     - React 18 + Vite
- prometheus/   - Prometheus monitoring configuration
- grafana/      - Pre-built Grafana dashboard export
- database/     - Optional PostgreSQL schema + sample data
- README.md     - This file

## Prerequisites

- Java 17
- Maven
- Node.js and npm
- Prometheus installed locally (add to environment variable path)
- Grafana installed locally

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

### Prometheus

Start Prometheus locally and ensure it is scraping your backend metrics endpoint.

Typical config target:

http://localhost:8080/actuator/prometheus

Prometheus runs on:
http://localhost:9090

### Grafana Setup (Monitoring Dashboard)

Grafana is used to visualize system metrics collected via Prometheus.

**Location:**
grafana/dashboard.json

**Setup:**
1. Install Grafana locally
2. Start Grafana (http://localhost:3000)
3. Go to Dashboards → Import
4. Upload grafana/dashboard.json
5. Select Prometheus as datasource
6. Click Import

**Step 1: Start Grafana**

Open Grafana in your browser:
http://localhost:3000

Default login:
Username: admin
Password: admin

**Step 2: Add Prometheus Data Source**

Main Menu Button → Go to Connections → Data Sources
1. Select Prometheus
2. Set URL:
http://localhost:9090
3. Click Save & Test

**Step 3: Import Dashboard**

Go to Dashboards → + button → Import Dashboard
1. Upload file from repo: grafana/dashboard.json
2. Select Prometheus as data source
3. Click Import

---

## Startup Scripts

The repository now includes automation scripts for easy development setup.

### start_all.bat

This script starts all required services in the correct order:

**What it does:**
- Starts backend (Spring Boot) on port 8080
- Waits 10 seconds for backend initialization
- Starts frontend (React) on port 5173
- Waits 5 seconds for frontend initialization
- Starts Prometheus on port 9090
- Displays all service URLs

**How to use:**
```bash
start_all.bat
```

**Services started:**
- Backend: http://localhost:8080
- Frontend: http://localhost:5173
- Prometheus: http://localhost:9090
- Actuator: http://localhost:8080/actuator
- Metrics: http://localhost:8080/actuator/prometheus

### generate-traffic.ps1

This PowerShell script simulates realistic API traffic for monitoring purposes.

**What it does:**
- Continuously generates API requests to all backend endpoints
- Randomizes investor IDs, product IDs, and withdrawal amounts
- Tests both read operations (GET) and write operations (POST)
- Includes occasional invalid IDs to test error handling
- Throttles requests to prevent overwhelming the backend
- Displays real-time request results in the console

**Why randomization exists:**
- Simulates realistic user behavior
- Tests various scenarios (valid and invalid requests)
- Generates diverse metrics for Grafana visualization
- Helps identify performance issues under different load patterns

**How throttling works:**
- Script pauses for 1.2 seconds between request cycles
- Prevents overwhelming the backend with too many requests
- Creates smooth, readable Grafana charts
- Allows backend to process requests at a sustainable rate

**Endpoints hit:**
- GET /api/investors/{id} - Investor retrieval
- GET /api/portfolios/investor/{investorId} - Portfolio retrieval
- GET /api/withdrawals/investor/{investorId} - Withdrawal history
- GET /api/withdrawals/export?investorId={id} - CSV export (heavy endpoint)
- POST /api/withdrawals - Withdrawal submission (write load)

**How to use:**
```powershell
powershell -ExecutionPolicy Bypass -File generate-traffic.ps1
```

Press CTRL + C to stop the traffic generator.

---

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

## Testing Error Handling

The application implements comprehensive error handling with specific HTTP status codes and error messages. Here's how to trigger and test each error scenario:

### 1. Age Restriction Error (403 Forbidden)

**Scenario:** Investor under 65 attempts withdrawal from retirement product

**How to Trigger:**
- Select investor Aisha Patel (age 45), Sipho Dlamini (age 30), Siseko Makomazi (age 25), or Nomsa Ngubane (age 35)
- Attempt to withdraw from a RETIREMENT product type

**Expected Result:**
- HTTP 403 Forbidden
- Error message: "Retirement withdrawals are only allowed for investors older than 65."

**Test via UI:**
1. Switch to investor Aisha (age 45) using navbar dropdown
2. Navigate to Withdraw page
3. Select "Pension Fund B" (RETIREMENT type)
4. Enter any valid amount
5. Submit withdrawal
6. Error message displayed: "Retirement withdrawals are only allowed for investors older than 65."

**Test via API:**
```bash
curl -X POST http://localhost:8080/api/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"investorId": 2, "productId": 3, "amount": 1000}'
```

### 2. Insufficient Balance Error (400 Bad Request)

**Scenario A: Withdrawal amount exceeds product balance**

**How to Trigger:**
- Select any investor
- Attempt to withdraw more than the product's available balance

**Expected Result:**
- HTTP 400 Bad Request
- Error message: "Withdrawal amount exceeds available balance."

**Test via UI:**
1. Select investor Thabo (age 70)
2. Navigate to Withdraw page
3. Select "Money Market" (SAVINGS type, balance: R80,000)
4. Enter amount: R100,000
5. Submit withdrawal
6. Error message displayed: "Withdrawal amount exceeds available balance."

**Scenario B: Withdrawal amount exceeds 90% of balance**

**How to Trigger:**
- Select any investor
- Attempt to withdraw more than 90% of the product's balance

**Expected Result:**
- HTTP 400 Bad Request
- Error message: "Withdrawal cannot exceed 90% of the product balance (max: [amount])."

**Test via UI:**
1. Select investor Thabo (age 70)
2. Navigate to Withdraw page
3. Select "Money Market" (SAVINGS type, balance: R80,000)
4. Enter amount: R75,000 (exceeds 90% of R80,000 = R72,000)
5. Submit withdrawal
6. Error message displayed: "Withdrawal cannot exceed 90% of the product balance (max: 72000.00)."

**Test via API:**
```bash
curl -X POST http://localhost:8080/api/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"investorId": 1, "productId": 2, "amount": 100000}'
```

### 3. Resource Not Found Error (404 Not Found)

**Scenario:** Requesting non-existent investor, portfolio, or product

**How to Trigger:**
- Use an invalid investor ID or product ID in API calls

**Expected Result:**
- HTTP 404 Not Found
- Error message: "Investor not found: [id]" or "Portfolio not found for investor: [id]" or "Product not found: [id]"

**Test via API:**
```bash
# Invalid investor
curl http://localhost:8080/api/investors/999

# Invalid portfolio
curl http://localhost:8080/api/portfolios/investor/999

# Invalid product in withdrawal
curl -X POST http://localhost:8080/api/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"investorId": 1, "productId": 999, "amount": 1000}'
```

### 4. Validation Error (400 Bad Request)

**Scenario:** Missing required fields or invalid input data

**How to Trigger:**
- Submit withdrawal request without required fields (investorId, productId, amount)
- Submit negative or zero amount

**Expected Result:**
- HTTP 400 Bad Request
- Error message with field-specific validation errors

**Test via UI:**
1. Navigate to Withdraw page
2. Leave product or amount fields empty
3. Submit withdrawal
4. Client-side validation prevents submission

**Test via API:**
```bash
# Missing fields
curl -X POST http://localhost:8080/api/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"investorId": 1}'

# Negative amount
curl -X POST http://localhost:8080/api/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"investorId": 1, "productId": 1, "amount": -100}'
```

### 5. Generic Server Error (500 Internal Server Error)

**Scenario:** Unexpected server-side errors

**How to Trigger:**
- This would typically occur due to database connection issues, null pointer exceptions, or other unexpected errors

**Expected Result:**
- HTTP 500 Internal Server Error
- Error message: [Exception message]

**Note:** This error type is a fallback for unhandled exceptions. The application's comprehensive error handling should prevent most scenarios from reaching this state.

### Running Error Handling Tests

**Unit Tests:**
```bash
cd backend
mvn test
```

This runs:
- `WithdrawalValidatorTest` - Tests business rule validation
- `GlobalExceptionHandlerTest` - Tests error response formatting

**Manual Testing Checklist:**
- [ ] Age restriction error with investor under 65
- [ ] Balance exceeded error with amount > balance
- [ ] 90% limit exceeded error with amount > 90% of balance
- [ ] Resource not found error with invalid IDs
- [ ] Validation error with missing fields
- [ ] Valid withdrawal succeeds and updates balance

## Advanced Requirements Implemented

1. Global exception handling - GlobalExceptionHandler with RestControllerAdvice
2. DTO layer - Request/response DTOs separate from JPA entities
3. Input validation - Jakarta Bean Validation annotations
4. Unit tests - WithdrawalValidatorTest with JUnit 5
5. UI validation - Client-side validation in WithdrawalForm component

## Bonus Implementation
Implemented Prometheus for scraping metrics and Grafana Visualization 

## How to Change Investor Profiles

To switch between different investors in the frontend:

**Option 1: Using the Navbar Dropdown**
- Use the investor dropdown in the navbar to select a different investor
- The dropdown shows all available investors with their names and ages
- Color indicator shows the current investor's assigned color

**Option 2: Manual Configuration**
1. Open frontend/src/App.jsx
2. Change the currentInvestorId initial value (line 8)
3. Available investor IDs from data.sql:
   - ID 1: Thabo Mokoena (age 70) - Can withdraw from retirement products
   - ID 2: Aisha Patel (age 45) - Cannot withdraw from retirement products
   - ID 3: Sipho Dlamini (age 30) - Cannot withdraw from retirement products
   - ID 4: Siseko Makomazi (age 25) - Cannot withdraw from retirement products
   - ID 5: Nomsa Ngubane (age 35) - Cannot withdraw from retirement products

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
