package org.monads;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FList<T> implements Functor<T,Functor<?,?>> {

    private final ImmutableList<T>  list;

    public FList(Iterable<T> it) {
        this.list = ImmutableList.copyOf(it);
    }
    
    @Override
    public <R> FList<R>  map(Function<T, R> f) {

        List<R> res = new ArrayList<>(list.size());
        for(T it: list) {
           res.add(f.apply(it));
        }
        return new FList<>(res);
    }
}
