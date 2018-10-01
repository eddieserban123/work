package com.example.rest.webservices.restfulwebservices.filtering;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveMyBean() {
        MyBean mb = new MyBean("value1", "value2", "value3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
                filterOutAllExcept("field1","field2");

        FilterProvider filters = new SimpleFilterProvider().addFilter("MyBeanFilter",filter);

        MappingJacksonValue mjv = new MappingJacksonValue(mb);
        mjv.setFilters(filters);

        return mjv;

    }


    @GetMapping("/filtering-all")
    public List<MyBean> retrieveMyListBean() {



        return  Arrays.asList(new MyBean("value1", "value2", "value3"),
                new MyBean("value1", "value2", "value3"));
    }



}
