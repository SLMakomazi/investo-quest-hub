@echo off
setlocal

// Set the window title for easy identification
title Enviro365 Startup

echo ========================================
echo Starting Enviro365 Platform...
echo ========================================

echo.

// Start the Spring Boot backend first
// Backend needs time to initialize before frontend can connect
echo ========================================
echo Starting Backend...
echo ========================================
// start opens a new command window with the title "Enviro365 Backend"
// cmd /k keeps the window open after the command completes
// cd /d backend changes directory to the backend folder
// mvn spring-boot:run starts the Spring Boot application
start "Enviro365 Backend" cmd /k "cd /d backend && mvn spring-boot:run"

// Wait 10 seconds for backend to fully start
// This ensures the backend API is ready before frontend attempts to connect
timeout /t 10 >nul

// Start the React frontend development server
echo ========================================
echo Starting Frontend...
echo ========================================
// start opens a new command window with the title "Enviro365 Frontend"
// cd /d frontend changes directory to the frontend folder
// npm run dev starts the Vite development server on port 5173
start "Enviro365 Frontend" cmd /k "cd /d frontend && npm run dev"

// Wait 5 seconds for frontend to start
// This gives the Vite server time to initialize
timeout /t 5 >nul

// Start Prometheus for metrics monitoring
echo ========================================
echo Starting Prometheus...
echo ========================================

// start opens a new command window with the title "Prometheus"
// %~dp0 gets the directory where this batch file is located
// --config.file points to the prometheus.yml configuration file
start "Prometheus" cmd /k "C:\Users\Wipro\Tools\prometheus\prometheus.exe --config.file=%~dp0prometheus\prometheus.yml"

echo.
echo ========================================
echo SERVICES STARTED
echo ========================================
// Display the URLs for all running services
echo Backend    : http://localhost:8080
echo Frontend   : http://localhost:5173
echo Prometheus : http://localhost:9090
echo Actuator   : http://localhost:8080/actuator
echo Metrics    : http://localhost:8080/actuator/prometheus
echo ========================================
echo.

// Pause keeps the startup window open so the user can see the output
pause