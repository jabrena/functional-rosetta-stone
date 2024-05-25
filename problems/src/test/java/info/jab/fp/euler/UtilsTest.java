package info.jab.fp.euler;

import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;

import info.jab.fp.euler.Utils;
import reactor.test.StepVerifier;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {


    @Test
    public void given_JavaStreamSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(Utils.JavaStreams.fibonacci().skip(1)
                .mapToLong(BigInteger::longValue)
                .takeWhile(x -> x <= 100)
                .mapToObj(x -> Long.parseLong(String.valueOf(x))))
                .isEqualTo(expectedFibonacci100);
    }

    @Test void give_JavaStreamFactorialTest() {

        assertThat(Utils.JavaStreams.factorialTrampoline.apply(10L)).isEqualTo(BigInteger.valueOf(3628800));
        assertThat(Utils.JavaStreams.factorialStream.apply(10L)).isEqualTo(BigInteger.valueOf(3628800));

        assertThat(Utils.JavaStreams.factorialTrampoline.apply(100L)).isEqualTo(new BigInteger("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000"));
        assertThat(Utils.JavaStreams.factorialStream.apply(100L)).isEqualTo(new BigInteger("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000"));
    }


}