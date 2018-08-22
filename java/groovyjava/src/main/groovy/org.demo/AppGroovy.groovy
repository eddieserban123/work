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
                ["1", 12, "02-03-2018"],
                ["2",  15, "02-03-2018"],
                ["2",  18, "02-03-2018"]
        ] as Trade[]


        def res = list.toList().unique().groupBy({trade ->trade.aqrid}).values().collect({el->el.size()}).find({n->n>=2})

        println(res==null)


    }
}