package org.example.login.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class PrincipalControllerAdvice {

    @ModelAttribute("currentUser")
    Principal principal(Principal p) {
        return p;
    }

}
