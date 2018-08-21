package org.demo;

import org.demo.beans.Trade;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

//https://www.jorgemanrubia.com/2009/10/10/evaluating-code-dynamically-in-groovy/

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Iterate it = new Iterate();
        List<Trade> tr = new ArrayList<>();
        tr.add(new Trade(10));
        tr.add(new Trade(20));

       it.sum(tr);




        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");

        if(null==engine)
        {
            System.err.println("Could not find groovy script engine,make sure to include groovy engine in your classpath");
        }
        try
        {



            engine.put("tr", tr);
            System.out.println(engine.eval("tr.stream().mapToInt(new ToIntFunction<Trade>() {\n" +
                    "                                                        public int applyAsInt(Trade value) {\n" +
                    "                                                            return value.getVal(); }}).sum();"));
        }
        catch (ScriptException e)
        {
            e.printStackTrace();
        }
    }
}
