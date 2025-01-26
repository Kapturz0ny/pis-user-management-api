package com.example.user_management_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String sayHello() {
        String output =  "\nHello World!";
        return output;
    }
}
