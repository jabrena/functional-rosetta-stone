package info.jab.fp.java8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class FizzBuzzTest {

    @Test
    public void fizzBuzzMapperTest() {

        FizzBuzz fizzBuzz = new FizzBuzz();

        then(fizzBuzz.fizzBuzzMapper.apply(3)).isEqualTo("Fizz");
        then(fizzBuzz.fizzBuzzMapper.apply(5)).isEqualTo("Buzz");
        then(fizzBuzz.fizzBuzzMapper.apply(15)).isEqualTo("FizzBuzz");
        then(fizzBuzz.fizzBuzzMapper.apply(2)).isEqualTo("2");
    }

}