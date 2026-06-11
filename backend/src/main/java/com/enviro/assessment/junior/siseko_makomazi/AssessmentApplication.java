package com.enviro.assessment.junior.siseko_makomazi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 * Entry point for the Enviro365 Investments withdrawal notice system.
 */
@SpringBootApplication
public class AssessmentApplication {
    public static void main(String[] args) {
        // args is declared by the JVM when the application starts.
        // SpringApplication.run boots the embedded server, loads beans, and starts the REST API.
        SpringApplication.run(AssessmentApplication.class, args);
    }
}
