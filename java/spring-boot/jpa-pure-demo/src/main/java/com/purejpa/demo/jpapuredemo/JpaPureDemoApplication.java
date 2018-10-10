package com.purejpa.demo.jpapuredemo;

import com.purejpa.demo.jpapuredemo.person.PersonServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class JpaPureDemoApplication implements CommandLineRunner {


    private Logger logger = LoggerFactory.getLogger(JpaPureDemoApplication.class);
    @Autowired
    PersonServiceDao persService;

    public static void main(String[] args) {

        SpringApplication.run(JpaPureDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        persService.getAllPersons().stream().forEach( p ->
                logger.info(p.toString()));
    }
}
