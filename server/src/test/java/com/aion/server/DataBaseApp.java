package com.aion.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.aion.server.database.repositories")
public class DataBaseApp {

    public static void main(String[] args) {
        SpringApplication.run(DataBaseApp.class, args);
    }
}
