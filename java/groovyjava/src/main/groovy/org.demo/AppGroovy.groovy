package org.demo

import com.fasterxml.jackson.databind.ObjectMapper

/*
https://www.jorgemanrubia.com/2009/10/10/evaluating-code-dynamically-in-groovy/
 */

class AppGroovy {



    static void main(String[] args) {
//        def shell = new GroovyShell()
//        def code = new File("/opt/say.script").text
//        code = "{->${code}}"
//
//        def context = new Context()
//
//        def closure = shell.evaluate(code)
//        closure.delegate = context
//        closure()
//
//
        Binding binding = new Binding()
        def shell1 = new GroovyShell(binding)


        def list = [
                [1, "AAQ"],
                [2, "MMG"],
                [3, "TRT"]
        ] as Trade[]


        binding.setVariable("list", list)


        final File file = new File("/opt/rules1.json")

        ObjectMapper mapper = new ObjectMapper()
        Map<String, String> map = mapper.readValue(file, Map.class)

        String script = map.get("script")

        Object result = shell1.evaluate(script)
        println(result)


    }
}