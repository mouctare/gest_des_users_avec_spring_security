package com.springsecurity.springservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PostMapping("/salutation")
    public String hello(){
        return "Bonjour";
    }
}
