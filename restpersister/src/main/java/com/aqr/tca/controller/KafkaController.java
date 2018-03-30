package com.aqr.tca.controller;


import com.aqr.tca.model.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Person userForm() {
        return new Person("Bill Gates");
    }
}
