package com.aion.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping(value = "/")
    public String index() {
        //Do nothing for the time
        return null;
    }
}
