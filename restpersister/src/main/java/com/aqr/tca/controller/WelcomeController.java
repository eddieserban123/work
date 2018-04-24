package com.aqr.tca.controller;

import com.aqr.tca.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class WelcomeController {

    @Autowired
    TopicService topicService;

    @GetMapping("/")
    public String index(Model model) {
     //   model.addAttribute("topics", topicService.getTopics().getTopics());  // i know is wrong
        model.addAttribute("topics", Arrays.asList("topic1","topic","topic3","topic4"));
        return "index";
    }
}
