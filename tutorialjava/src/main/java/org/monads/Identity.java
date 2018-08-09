package org.monads;

import java.util.function.Function;

public class Identity<T> implements Functor<T,Identity<?>> {

    private final T val;

    public Identity(T val) {
        this.val = val;
    }

    @Override
    public <R> Identity<R> map(Function<T, R> f) {
        final R res = f.apply(val);
        return new Identity<>(res);
    }
}
