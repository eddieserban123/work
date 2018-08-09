package org.monads;

import java.util.function.Function;

public class MOptional<T> implements Monad<T, Monad<?, ?>> {

    private final T valOrNull;

    public MOptional(T valOrNull) {
        this.valOrNull = valOrNull;
    }

    @Override
    public Monad<?, ?> flatMap(Function<T, Monad<?, ?>> f) {
        if (valOrNull == null) {
            return empty();
        }
        return f.apply(valOrNull);
    }

    @Override
    public <R> Monad<?, ?> map(Function<T, R> f) {
        if (valOrNull == null) {
            return empty();
        }
        return of(f.apply(valOrNull));
    }

    public static <R> MOptional<R> of(R a) {
        return new MOptional<>(a);
    }

    private static <R> MOptional<R> empty() {
        return new MOptional<>(null);
    }

    public static MOptional<Integer> tryParse(String str) {

        try {
            Integer res = Integer.valueOf(str);
            return of(res);
        } catch (NumberFormatException ex) {
            return empty();
        }
    }
}
