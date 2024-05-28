package info.jab.fp.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.utils.TestLoggerExtension;

@ExtendWith(TestLoggerExtension.class)
public class FunctionalInterfacesExamplesTest {

    private static FunctionalInterfacesExamples fiExamples;

    @BeforeAll
    public static void setUp() {
        fiExamples = new FunctionalInterfacesExamples();
    }

    @Test
    public void testUsingFunction1() {

        //Given

        //When
        var result = fiExamples.usingFunctions1();

        //Then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testUsingFunction2() {

        //Given

        //When
        var result = fiExamples.usingFunctions2();

        //Then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testUsingFunction3() {

        //Given

        //When
        var result = fiExamples.usingFunctions3();

        //Then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testUsingFunction4() {

        //Given

        //When
        var result = fiExamples.usingFunctions4();

        //Then
        assertThat(result).isEqualTo(4);
    }

    @Test
    public void testUsingFunction5() {

        //Given

        //When
        var result = fiExamples.usingFunctions5();

        //Then
        assertThat(result).isEqualTo(4);
    }

    @Test
    public void testUsingFunction6() {

        //Given

        //When
        var result = fiExamples.usingFunctions6();

        //Then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testUsingFunction7() {

        //Given

        //When
        var result = fiExamples.usingFunctions7();

        //Then
        assertThat(result).isEqualTo(12);
    }

    @Test
    public void testUsingFunction8() {

        //Given

        //When
        var result = fiExamples.usingFunctions8();

        //Then
        assertThat(result).isEqualTo(9);
    }

    @Test
    public void consumerInterfaceTest() {

        Consumer<Integer> print = System.out::println;
        Consumer<Integer> multiplicationTable = x -> IntStream.rangeClosed(1, 10).boxed().skip(1).forEach(y -> System.out.println(y * x));
        
        IntStream.rangeClosed(1, 10).boxed()
            .forEach(print.andThen(multiplicationTable)::accept);
    }

    @Test
    public void predicateInterfaceTest() {

        List<Integer> expectedList = Arrays.asList(2,4,6,8,10);
        Predicate<Integer> isPair = x -> x % 2 == 0;

        assertThat(IntStream.rangeClosed(1, 10).boxed()
                .filter(isPair)
                .collect(Collectors.toList()))
                .isEqualTo(expectedList);
    }
}