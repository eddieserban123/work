package org.monads;

public class App {
    public static void main(String[] args) {
        Identity<String> id = new Identity<>("Ana are mere").
                map(String::toLowerCase);

        FOptional<FOptional<Integer>> res = FOptional.of("42").map(FOptional::tryParse);

        MOptional<Integer> resM = (MOptional<Integer>) MOptional.of("42").flatMap(MOptional::tryParse);


        FOptional<Integer> num1 = FOptional.of(41);//...
        FOptional<FOptional<Integer>> num2 = FOptional.of(50).map(v -> FOptional.of(v));

    }
}
