package com.aqr.tca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class SpringRootConfig {

    @Autowired
    private ApplicationContext context;

        //spring stuff beans


}