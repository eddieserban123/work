package com.example.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {


    @GetMapping("/hello")
    public String sayHello(@RequestParam(name = "name", defaultValue = "World") String name, Model model){
        model.addAttribute("user",name);
        return "hello";
    }
}
