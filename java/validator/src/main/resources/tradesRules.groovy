import groovy.transform.CompileStatic
import model.Trades

@CompileStatic
Tuple validateTrades(Trades alltrades) {
    def residualTrades = alltrades.trades.unique().groupBy({ trade -> trade.aqrId }).values().findAll({ l -> l.size() >= 2 })
    if (residualTrades.size() > 0) {
        def result = residualTrades.collect({ it[0].aqrId })
        println("Test failed! The Trades with AqrIds in" + result + " do not match the business rule!")
        return new Tuple(false, result)
    } else {
        println("Test passed !");
        return new Tuple(true, [])
    }
}
