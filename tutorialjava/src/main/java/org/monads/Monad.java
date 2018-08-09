package org.monads;

import java.util.function.Function;

public interface Monad<T, M extends Monad<?, ?>> extends Functor<T, M> {

    M flatMap(Function<T, M> f);
}
