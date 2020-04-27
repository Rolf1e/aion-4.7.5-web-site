package com.aion.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class AionServer {

    public static void main(String[] args) {
        SpringApplication.run(AionServer.class, args);
    }

//    TODO: - /!\ TU /!\
//    - had mail to handle html
//    - Integration Tests
}

