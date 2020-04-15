package com.aion.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class AionServer {

    public static void main(String[] args) {
        SpringApplication.run(AionServer.class, args);
    }

//    TODO: - /!\ TU /!\
//    - handle mail in return
//    - paypal
//    - had mail to handle html
//    - refactor with service
}

