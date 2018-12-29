package org.example.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String getIndex() {
        return "hidden";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout-success")
    public String logout() {
        return "logout";
    }


}
