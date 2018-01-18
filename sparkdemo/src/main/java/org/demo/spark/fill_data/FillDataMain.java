package org.demo.spark.fill_data;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

public class FillDataMain {
    private static int MAX_NO_OF_DAYS = 1000;
    private static int MAX_NO_OF_EVENTS = 1000;
    private static int MAX_EVENT_VALUE = 86400;

    public static void main(String[] args) {

        try (Cluster cluster = Cluster.builder()
                .addContactPoint("10.64.134.27")
                .withPort(9042)
                .build(); Session session = cluster.connect("test1")) {
            cluster.getConfiguration().getCodecRegistry()
                    .register(LocalDateCodec.instance);
            PreparedStatement ps = session.prepare("" +
                    "INSERT INTO trades_by_time1(day , offset , market_event , aqrid , tradetime_millis ) VALUES (?, ?, ?, ?, ?)");


            int totRemDays = MAX_NO_OF_DAYS;
            int curMonDays = 0;

            LocalDate date = LocalDate.of(2008, Month.JANUARY, 1);

            while (totRemDays > 0) {
                curMonDays = date.getMonth().maxLength();
                curMonDays = totRemDays >= curMonDays ? curMonDays : totRemDays;
                totRemDays -= date.getMonth().maxLength();
                System.out.println(String.format("%d-%s:", date.getYear(), date.getMonth().toString()));
                for (int i = 1; i <= curMonDays; i++) {
                    System.out.println(String.format("\t%d:", i));
                    for (int k = 1; k <= MAX_NO_OF_EVENTS; k++) {
                        session.execute(ps.bind(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()).plusDays(i),
                                genOff(),
                                genMarketEvent(),
                                10,
                                genTimeInMilis()));
//                        System.out.println(String.format("\t\t %d", ThreadLocalRandom.current().nextInt(1, MAX_EVENT_VALUE + 1)));
                    }
                }
                date = date.plusMonths(1);
            }
        }
    }

    private static int genOff() {
        return ThreadLocalRandom.current().nextInt(1, MAX_EVENT_VALUE);
    }

    private static String genMarketEvent() {
        int n = ThreadLocalRandom.current().nextInt(1, 5);
        switch (n) {
            case 1:
                return "Equity";
            case 2:
                return "Future";
            case 3:
                return "Share";
            default:
                return "Quotes";
        }
    }

    private static long genTimeInMilis() {
        return ThreadLocalRandom.current().nextInt(100, 2000);
    }
}
