package com.datapirates.touristguideapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(path = "/")
    public String hello(){
        return "Hello Springboot";
    }
}
