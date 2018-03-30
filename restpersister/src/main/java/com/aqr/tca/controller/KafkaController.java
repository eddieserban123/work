package com.aqr.tca.controller;


import com.aqr.tca.config.utils.ConsumerBuilder;
import com.aqr.tca.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Person users() {
        return new Person("Bill Gates");
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> getDataFromTopics() {
        ConsumerBuilder.createKafkaConsumer();
        return new ResponseEntity<String>("anaremere", HttpStatus.CREATED);
    }
}



