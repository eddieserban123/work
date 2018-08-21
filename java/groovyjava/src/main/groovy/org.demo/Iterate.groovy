package org.demo

import org.demo.beans.Trade

class Iterate {

    double sum(List<Trade> list){
        return list*.val.sum()
    }
}
