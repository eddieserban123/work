package org.learn.spliterator.demo03combinerusage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by eduard on 07/09/17.
 */
public class PaymentCollector implements Collector<Payment, PaymentCollector.Acumulator,Map<String, Integer>> {

    public class Total {
        public Map<String, Integer> values = new HashMap<>();
    }

    public class Acumulator {
        public Map<String, Integer> values = new HashMap<>();
    }

    @Override
    public Supplier<Acumulator> supplier() {
        return Acumulator::new;
    }

    @Override
    public BiConsumer<Acumulator, Payment> accumulator() {
        return (a, p) -> {
            if(null == a.values.computeIfPresent(p.getCategory(),(k,v) -> v + p.getAmount())) {
                a.values.put(p.getCategory(), p.getAmount());
            }
        };
    }

    @Override
    public BinaryOperator<Acumulator> combiner() {
        return null;
    }

    @Override
    public Function<Acumulator, Map<String, Integer>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
