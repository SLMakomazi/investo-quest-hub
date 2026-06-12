# --- CONFIGURATION ---
# Base URL points to the Spring Boot backend API endpoint
$BaseUrl = "http://localhost:8080/api"

# Headers set the content type to JSON for API requests
$Headers = @{
    "Content-Type" = "application/json"
    "Accept"       = "application/json"
}

# Display startup message to the user
Write-Host "🚀 Starting Enviro365 Traffic Generator..." -ForegroundColor Green
Write-Host "Press CTRL + C to stop.`n" -ForegroundColor Yellow

# Real system values matching the database
# Investors 1-5 exist in the system
$Investors = 1..5
# Products 101-102 exist in the system
$Products  = 101..102

# Infinite loop to continuously generate traffic
while ($true) {

    # Randomized realistic load inputs
    # Randomly selects investor ID, occasionally including invalid IDs to test error handling
    $InvestorId = ($Investors + (999 | Get-Random -Count 1)) | Get-Random  # includes occasional bad ID
    # Randomly selects a product ID
    $ProductId  = $Products | Get-Random
    # Randomly selects an amount from predefined values to test different scenarios
    $Amount     = (500, 1500, 50000, 99999 | Get-Random)

    # --------------------------------------------------
    # 1. GET INVESTOR
    # --------------------------------------------------
    # Tests the investor retrieval endpoint
    try {
        Write-Host "GET /investors/$InvestorId" -NoNewline
        $r1 = Invoke-WebRequest "$BaseUrl/investors/$InvestorId" -UseBasicParsing -ErrorAction Stop
        Write-Host " -> $($r1.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host " -> ERROR" -ForegroundColor Red
    }

    # --------------------------------------------------
    # 2. GET PORTFOLIO
    # --------------------------------------------------
    # Tests the portfolio retrieval endpoint
    try {
        Write-Host "GET /portfolios/investor/$InvestorId" -NoNewline
        $r2 = Invoke-WebRequest "$BaseUrl/portfolios/investor/$InvestorId" -UseBasicParsing -ErrorAction Stop
        Write-Host " -> $($r2.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host " -> ERROR" -ForegroundColor Red
    }

    # --------------------------------------------------
    # 3. GET WITHDRAWAL HISTORY
    # --------------------------------------------------
    # Tests the withdrawal history retrieval endpoint
    try {
        Write-Host "GET /withdrawals/investor/$InvestorId" -NoNewline
        $r3 = Invoke-WebRequest "$BaseUrl/withdrawals/investor/$InvestorId" -UseBasicParsing -ErrorAction Stop
        Write-Host " -> $($r3.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host " -> ERROR" -ForegroundColor Red
    }

    # --------------------------------------------------
    # 4. EXPORT CSV (HEAVY ENDPOINT)
    # --------------------------------------------------
    # Tests the CSV export endpoint which is more resource-intensive
    try {
        Write-Host "GET /withdrawals/export?investorId=$InvestorId" -NoNewline
        $r4 = Invoke-WebRequest "$BaseUrl/withdrawals/export?investorId=$InvestorId" -UseBasicParsing -ErrorAction Stop
        Write-Host " -> CSV OK" -ForegroundColor Green
    } catch {
        Write-Host " -> ERROR" -ForegroundColor Yellow
    }

    # --------------------------------------------------
    # 5. POST WITHDRAWAL (WRITE LOAD)
    # --------------------------------------------------
    # Builds the JSON request body for withdrawal submission
    $RequestBody = @{
        investorId = [long]$InvestorId
        productId  = [long]$ProductId
        amount     = [decimal]$Amount
    } | ConvertTo-Json

    # Tests the withdrawal submission endpoint
    # This generates write load and tests validation logic
    try {
        Write-Host "POST /withdrawals" -NoNewline
        $r5 = Invoke-WebRequest "$BaseUrl/withdrawals" `
            -Method Post `
            -Body $RequestBody `
            -Headers $Headers `
            -UseBasicParsing `
            -ErrorAction Stop

        Write-Host " -> $($r5.StatusCode)" -ForegroundColor Green
    } catch {
        Write-Host " -> VALIDATION / ERROR" -ForegroundColor Yellow
    }

    # --------------------------------------------------
    # THROTTLE (controls Grafana smoothness)
    # --------------------------------------------------
    # Sleep for 1.2 seconds between requests
    # This throttling prevents overwhelming the backend and creates smooth Grafana charts
    Write-Host "----------------------------------------"
    Start-Sleep -Milliseconds 1200
}