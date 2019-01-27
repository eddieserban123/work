package orig.example.authorizationsimple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/a")
    String sayGetA() {
        return "a";
    }

    @GetMapping("/b")
    String sayGetB() {
        return "b";
    }

    @PostMapping("/b")
    String sayPostB() {
        return "b";
    }
}
