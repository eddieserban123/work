package groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.lang.Tuple;
import model.Trades;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GroovyEval {
    private static Binding binding;
    private static GroovyShell shell;
    private static Script scrpt = null;

    static {
        try {
            binding = new Binding();
            shell = new GroovyShell(binding);
            URL url = Thread.currentThread().getContextClassLoader().getResource("tradesRules.groovy");
            scrpt = shell.parse(new File(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GroovyEval() {
    }

    public static boolean evalTrades(Trades trades) {
        binding.setVariable("alltrades", trades);
        binding.setVariable("tools", scrpt);
        Tuple ret = (Tuple) shell.evaluate("tools.validateTrades(alltrades)");
        return (boolean) ret.get(0);
    }
}