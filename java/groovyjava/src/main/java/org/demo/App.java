package org.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.demo.beans.Trade;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//https://www.jorgemanrubia.com/2009/10/10/evaluating-code-dynamically-in-groovy/

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Binding binding = new Binding();
        GroovyShell shell1 = new GroovyShell(binding);



        List<Trade> list = new ArrayList<>();

        list.add(new Trade("AAQ1","02-01-2018",12));
        list.add(new Trade("AAQ2","02-03-2018",23));
        list.add(new Trade("AAQ3","02-05-2018",17));
        list.add(new Trade("AAQ3","02-05-2018",20));




        binding.setVariable("list", list);


        URL url = Thread.currentThread().getContextClassLoader().getResource("rules1.json");
        final File file = new File(url.getPath());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(file, Map.class);

        String script = map.get("script");

        Object result = shell1.evaluate(script);
        System.out.println("result is : " + result);


    }
}
