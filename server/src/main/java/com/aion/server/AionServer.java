package com.aion.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//    - Better handling errors
//    - error in base64 with pwd
//    - Check if db handling is good
//    - Fix load out resource (mail template)

}

