package com.example.rest.webservices.restfulwebservices.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource msgSource;

    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-i18n")
    public String helloWorldi18n(@RequestHeader(name="Accept-Language",
            required = false) Locale locale){
        return msgSource.getMessage("good.morning.msg",null,locale);
    }



}
