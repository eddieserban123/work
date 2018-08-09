package org.monads;

import java.util.function.Function;

public class FOptional<T> implements Functor<T, Functor<?,?>> {

    private final T valOrNull;

    public FOptional(T valOrNull) {
        this.valOrNull = valOrNull;
    }


    @Override
    public <R> FOptional<R> map(Function<T, R> f) {
        if(valOrNull==null) {
            return empty();
        }
        return of(f.apply(valOrNull));
    }

    public static <R> FOptional<R> of(R a) {
        return new FOptional<>(a);
    }

    private static <R> FOptional<R> empty() {
        return new FOptional<>(null);
    }

    public static FOptional<Integer> tryParse(String str) {

        try {
          Integer res = Integer.valueOf(str);
          return of(res);
        } catch (NumberFormatException ex) {
            return empty();
        }
    }
}
