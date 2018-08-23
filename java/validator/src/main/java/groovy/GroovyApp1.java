package groovy;

import model.Trades;
import util.BusinessHelper;
import java.io.IOException;

public class GroovyApp1 {

    public static void main(String[] args) throws IOException {
        Trades trades = BusinessHelper.getTrades("2014-05-15");
        GroovyEval.evalTrades(trades);

    }
}
