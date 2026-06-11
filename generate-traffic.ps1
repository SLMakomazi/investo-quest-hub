# --- CONFIGURATION ---
$BaseUrl = "http://localhost:8080/api"

$Headers = @{
    "Content-Type" = "application/json"
    "Accept"       = "application/json"
}

Write-Host "🚀 Starting Enviro365 Traffic Generator..." -ForegroundColor Green
Write-Host "Press CTRL + C to stop.`n" -ForegroundColor Yellow

# Real system values
$Investors = 1..5
$Products  = 101..102

while ($true) {

    # Randomized realistic load inputs
    $InvestorId = ($Investors + (999 | Get-Random -Count 1)) | Get-Random  # includes occasional bad ID
    $ProductId  = $Products | Get-Random
    $Amount     = (500, 1500, 50000, 99999 | Get-Random)

    # --------------------------------------------------
    # 1. GET INVESTOR
    # --------------------------------------------------
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
    $RequestBody = @{
        investorId = [long]$InvestorId
        productId  = [long]$ProductId
        amount     = [decimal]$Amount
    } | ConvertTo-Json

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
    Write-Host "----------------------------------------"
    Start-Sleep -Milliseconds 1200
}