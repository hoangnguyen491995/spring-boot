package com.example.shoppApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example")
public class OttFeApplication {

    public static void main(String[] args) {

        SpringApplication.run(OttFeApplication.class, args);
    }

}
