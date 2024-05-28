package info.jab.fp.lambda;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FunctionalInterfacesExamples {

    public int usingFunctions1() {

        Function<String, Integer> myFunction = x -> x.length();

        Integer result1 = myFunction.apply("fp");

        return result1;
    }

    public int usingFunctions2() {

        Function<String, Integer> myFunction = param -> {
            return param.length();
        };

        Integer result1 = myFunction.apply("fp");

        return result1;
    }

    public int usingFunctions3() {

        Function<String, Integer> myFunction = param -> {
            return Stream.of(param)
                .map(String::length)
                .findAny()
                .get();
        };

        Integer result1 = myFunction.apply("fp");

        return result1;
    }

    public int usingFunctions4() {

        BiFunction<Integer, Integer, Integer> myFunction = (param1, param2) -> param1 + param2;

        Integer result1 = myFunction.apply(2,2);

        return result1;
    }

    @FunctionalInterface
    interface Square {
        Integer calculate(Integer x);
    }

    public int usingFunctions5() {

        Square power = x -> x * x;

        return power.calculate(2);
    }

    public String usingFunctions6() {

        Function<String, String> upppercase = x -> x.toUpperCase();
        Function<String, String> crop = x -> x.substring(Math.round(x.length()/2));
        Supplier<String> uuidSupplier = UUID.randomUUID()::toString;
        
        return crop
                .andThen(upppercase)
                .apply(uuidSupplier.get());
    }

    public Integer usingFunctions7() {

        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add      = (value) -> value + 3;
        Function<Integer, Integer> addThenMultiply = multiply.compose(add);
    
        return addThenMultiply.apply(3);
    }

    public Integer usingFunctions8() {

        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add      = (value) -> value + 3;
        Function<Integer, Integer> multiplyThenAdd = multiply.andThen(add);

        return multiplyThenAdd.apply(3);
    }
}