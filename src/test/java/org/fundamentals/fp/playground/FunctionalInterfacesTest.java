package org.fundamentals.fp.playground;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class FunctionalInterfacesTest {

    @Property
    public void l1_toConstant() {
        assertThat(FunctionalInterfaces.L1_toConstant().get()).isEqualTo(42);
    }

    @Property
    public void l2_toUpperCase() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String output = input.toUpperCase();

        assertThat(FunctionalInterfaces.L2_toUpperCase().apply(input)).isEqualTo(output);
    }

    @Property
    public void l3_toLong(Long input) {
        Function<String, Long> function = FunctionalInterfaces.L3_toLong();

        assertThat(function.apply(input.toString())).isEqualTo(input);
    }

    @Property
    public void l4_to42IntegerPredicateFalse(@InRange(minInt = 43) Integer input) {
        IntPredicate function = FunctionalInterfaces.L4_to42IntegerPredicate();

        assertThat(function.test(input)).isEqualTo(true);
    }

    @Property
    public void l4_to42IntegerPredicateTrue() {
        IntPredicate function = FunctionalInterfaces.L4_to42IntegerPredicate();

        assertThat(function.test(42)).isEqualTo(false);
    }

    @Property
    public void l5_toIntegerPredicate(@InRange(maxInt = Integer.MAX_VALUE - 1) Integer input) {
        Function<Integer, Predicate<Integer>> function = FunctionalInterfaces.L5_toIntegerPredicate();

        assertThat(function.apply(input).test(input)).isEqualTo(false);
        assertThat(function.apply(input).test(input + 1)).isEqualTo(true);

    }

    @Property
    public void l6_toURI() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String string = String.format("http://%s.com", input);
        URI uri = URI.create(string);

        assertThat(FunctionalInterfaces.L6_toURI().apply(string)).isEqualTo(uri);
    }

    @Property
    public void l7_toCallable(Integer input) throws Exception {
        Function<Supplier<Integer>, Callable<Integer>> function = FunctionalInterfaces.L7_toCallable();
        Supplier<Integer> supplier = () -> input;
        Callable<Integer> output = function.apply(supplier);

        assertThat(supplier.get()).isEqualTo(output.call());
    }

    @Property
    public void l8_functionComposition(@InRange(maxInt = (Integer.MAX_VALUE / 2) - 2) Integer input) {

        BinaryOperator<Function<Integer, Integer>> combiner = FunctionalInterfaces.L8_functionComposition();

        Function<Integer, Integer> f1 = i -> i + 2;
        Function<Integer, Integer> f2 = i -> i * 2;
        Integer result = combiner.apply(f1, f2).apply(input);
        Assertions.assertThat(result).isEqualTo((input + 2) * 2);
    }
}