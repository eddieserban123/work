package org.demo.webserver;

import java.util.function.Function;

public class App1 {

    public static void main(String[] args) {
        //avg(add(2,3));
        Function<Integer, Integer> add = num -> num + 2;
        Function<Integer,Integer> sqr = val-> val * val;
        System.out.println(add.andThen(sqr).apply(5));
        System.out.println(add.compose(sqr).apply(5));




    }




}
