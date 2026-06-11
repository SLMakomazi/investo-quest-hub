package com.enviro.assessment.junior.siseko_makomazi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) configuration.
 * Allows the frontend running on different ports to access the backend API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry r) {
        // r is declared as the method parameter by Spring MVC.
        // It registers which browser origins may call the backend API.
        r.addMapping("/api/**")
         // Only API routes are exposed to the frontend during development.
         .allowedOrigins("http://localhost:5173", "http://localhost:3000")
         // These HTTP methods cover reading data, creating withdrawals, and browser preflight checks.
         .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
    }
}
