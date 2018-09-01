package groovy;

import model.Trades;
import util.BusinessHelper;

import java.io.IOException;

import static groovy.GroovyEval.evalTrades;

public class GroovyApp {

    public static void main(String[] args) throws IOException {

        Trades trades = BusinessHelper.getTrades("2014-05-15");
        evalTrades(trades);

    }
}
