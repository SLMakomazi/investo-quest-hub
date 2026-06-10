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
        // Allow CORS requests from frontend development servers
        r.addMapping("/api/**")
         .allowedOrigins("http://localhost:5173", "http://localhost:3000")
         .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
    }
}
