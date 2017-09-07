package org.learn.spliterator.demo03combinerusage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduard on 07/09/17.
 */
public class App {

    public static void main(String[] args) {
        List<Payment>  payments = new ArrayList<>();
        fillPayments(payments);
        PaymentCollector.Total total = payments.stream().collect(new PaymentCollector<>());




    }

    private static void fillPayments(List<Payment> payments) {
        for (int i=0; i<1000; i++) {
            payments.add(new Payment(10,"A"));
            payments.add(new Payment(20,"A"));
            payments.add(new Payment(30,"A"));
            // total = 60

            payments.add(new Payment(20,"B"));
            payments.add(new Payment(30,"B"));
            payments.add(new Payment(40,"B"));
            payments.add(new Payment(50,"B"));
            payments.add(new Payment(60,"B"));
            // total = 200

            payments.add(new Payment(30,"C"));
            payments.add(new Payment(30,"C"));
            payments.add(new Payment(20,"C"));
            // total = 80
        }
    }
}
