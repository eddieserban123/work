package orig.example.authorizationsimple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/root")
    String getRoot() {
        return "root";
    }

}
