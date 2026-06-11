@echo off
setlocal

title Enviro365 Startup

echo ========================================
echo Starting Enviro365 Platform...
echo ========================================

echo.

echo ========================================
echo Starting Backend...
echo ========================================
start "Enviro365 Backend" cmd /k "cd /d backend && mvn spring-boot:run"

timeout /t 10 >nul

echo ========================================
echo Starting Frontend...
echo ========================================
start "Enviro365 Frontend" cmd /k "cd /d frontend && npm run dev"

timeout /t 5 >nul

echo ========================================
echo Starting Prometheus...
echo ========================================

start "Prometheus" cmd /k "C:\Users\Wipro\Tools\prometheus\prometheus.exe --config.file=%~dp0prometheus\prometheus.yml"

echo.
echo ========================================
echo SERVICES STARTED
echo ========================================
echo Backend    : http://localhost:8080
echo Frontend   : http://localhost:5173
echo Prometheus : http://localhost:9090
echo Actuator   : http://localhost:8080/actuator
echo Metrics    : http://localhost:8080/actuator/prometheus
echo ========================================
echo.
pause