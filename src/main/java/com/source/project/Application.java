package com.source.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
    killing work on 8080
    1 netstat -ano | findstr :8080
    2 taskkill /PID typeyourPIDhere /F
 */

@SpringBootApplication
public class Application {

    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }


}
