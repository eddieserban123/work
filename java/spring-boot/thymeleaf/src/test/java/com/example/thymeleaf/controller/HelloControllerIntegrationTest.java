package com.example.thymeleaf.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerIntegrationTest {

    MockMvc  mvc;

    @Autowired
    WebApplicationContext wac;


    @Before
    public void preSetup() {
       mvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }


    @Test
    public void performWithoutName() throws Exception {
        mvc.perform(get("/name").
                accept(MediaType.TEXT_PLAIN)).
                andExpect(status().isOk()).
                andExpect(view().name("hello"));

    }

}
