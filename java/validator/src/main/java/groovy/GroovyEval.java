package groovy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.util.StringUtils;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import model.Trade;
import model.Trades;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class GroovyEval {

    private static Binding binding;
    private static GroovyShell groovyShell;
    private static List<String> differentTCAOpenPriceWithinAQRIDTradeDateScript;

    static {
        binding = new Binding();
        groovyShell = new GroovyShell(binding);

        URL url = Thread.currentThread().getContextClassLoader().getResource("tradeRules.json");
        File file = new File(url.getPath());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> map = null;
        try {
            map = objectMapper.readValue(file, Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Object> res = (Map<String, Object>) map.get("DifferentTCAOpenPriceWithinAQRIDTradeDateRule");

        differentTCAOpenPriceWithinAQRIDTradeDateScript = (List<String>)res.get("rule");
    }


    public GroovyEval() {
    }

    public static boolean evalTrades(Trades trades) {

        binding.setVariable("alltrades", trades);
        Object result = groovyShell.evaluate(StringUtils.join(differentTCAOpenPriceWithinAQRIDTradeDateScript, "\n"));
        System.out.println(result);
        return true;
    }


}
