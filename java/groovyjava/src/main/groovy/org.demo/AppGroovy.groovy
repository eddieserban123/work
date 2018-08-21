package org.demo
/*
https://www.jorgemanrubia.com/2009/10/10/evaluating-code-dynamically-in-groovy/
 */
class AppGroovy {




    static void main(String[] args) {
        def shell = new GroovyShell()
        def code = new File("/opt/say.script").text
        code = "{->${code}}"

        def context = new Context()

        def closure = shell.evaluate(code)
        closure.delegate = context
        closure()
    }
}