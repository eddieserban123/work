package org.demo

@groovy.transform.Canonical
@groovy.transform.EqualsAndHashCode(includes = ["aqrid","price"])
class Trade {
    String aqrid = ""
    Integer price = 0
    String tradeDate = ""


}
