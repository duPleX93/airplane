package com.airplane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot belépési pont. Indítja az alkalmazást és a beágyazott Tomcat szervert (pl. port 8080).
 */
@SpringBootApplication
public class AirplaneApplication {

    /** Indítja a REST API-t; a com.airplane csomagban lévő bean-ek automatikusan regisztrálódnak. */
    public static void main(String[] args) {
        SpringApplication.run(AirplaneApplication.class, args);
    }
}
