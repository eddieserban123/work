package org.monads;

import java.time.LocalDate;
import java.time.Month;

public class App {
    public static void main(String[] args) {
//        Identity<String> id = new Identity<>("Ana are mere").
//                map(String::toLowerCase);
//
//        FOptional<FOptional<Integer>> res = FOptional.of("42").map(FOptional::tryParse);
//
//        MOptional<Integer> resM = (MOptional<Integer>) MOptional.of("42").flatMap(MOptional::tryParse);
//
//
//        FOptional<Integer> num1 = FOptional.of(41);//...
//        FOptional<FOptional<Integer>> num2 = FOptional.of(50).map(v -> FOptional.of(v));

        Monad<Month, Monad<?, ?>> month = MOptional.of(Month.SEPTEMBER);
        Monad<Integer,Monad<?,?>> dayOfMonth = MOptional.of(12);

        Monad<LocalDate,Monad<?,?>> date = (Monad<LocalDate, Monad<?, ?>>) month.flatMap((Month m) ->
            dayOfMonth.map((Integer d) -> LocalDate.of(2018,m,d)));

        MOptional<Integer> m1 = MOptional.of(20);
        MOptional<Integer> m2 = MOptional.of(30);


        MOptional<Integer> m3 = (MOptional<Integer>) m1.flatMap((Integer i1) ->
        m2.map((Integer i2) -> i1+i2));

        MOptional<Integer> m4 = MOptional.of(40);

        MOptional ii = (MOptional) m4.flatMap(i3 ->
                MOptional.of(i3));

        MOptional<Integer> m5 = (MOptional<Integer>)
                m1.flatMap(i1 ->
                     m2.flatMap(i2->
                             m4.map(i3 ->
                                (i1+i2+i3))));






    }
}
