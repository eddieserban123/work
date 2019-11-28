package org.demomvc.modelattribute.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = BookController.class)
public class GeneralController {


    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("msg", "Welcome to the Romania!");
    }
}
