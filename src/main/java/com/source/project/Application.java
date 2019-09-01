package com.source.project;

import com.samskivert.mustache.Mustache;
import org.omg.CORBA.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }


}
