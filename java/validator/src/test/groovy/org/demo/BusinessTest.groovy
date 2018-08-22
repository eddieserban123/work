package org.demo

import model.Trades
import org.junit.Before
import org.junit.Test
import util.BusinessHelper

class BusinessTest {

    @Before
    void beforeTest() {

    }

    @Test
    void differentTCAOpenPriceWithinAQRIDTradeDateRule() {
        Trades alltrades = BusinessHelper.getTrades("02-03-2018")
        def residualTrades = alltrades.trades.unique().groupBy({ trade -> trade.aqrId }).values().findAll({ l -> l.size() >= 2 })
        if (residualTrades.size() > 0) {
            println("Test failed! The Trades with AqrIds in" + residualTrades.collect({
                it[0].aqrId
            }) + " do not match the business rule!")
        } else {
            println("Test passed !");
        }

    }
}
