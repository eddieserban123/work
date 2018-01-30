package org.kafka.bean;

import java.time.LocalDateTime;
import java.util.Random;

public class BuildShare {

    public static Share buildNow() {
        String[] names = {"ADBE", "MSFT", "AAPL","GOGL", "NKE"};
        Random rg = new Random();
        return new Share(names[rg.nextInt(names.length)], (long)rg.nextInt(100), LocalDateTime.now());
    }
}
