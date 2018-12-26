package org.example.inmemory.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class GreetingsRestController {

    @GetMapping("/greetings")
    String getGreetings(Principal principal) {
        return "hello " + principal.getName();
    }
}
