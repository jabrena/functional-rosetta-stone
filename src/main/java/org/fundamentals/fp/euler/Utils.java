package org.fundamentals.fp.euler;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class Utils {

    public static class JavaStreams {

        public static Stream<BigInteger> fibonacci() {

            Pair<BigInteger, BigInteger> seed = Pair.of(BigInteger.ZERO, BigInteger.ONE);
            UnaryOperator<Pair<BigInteger, BigInteger>> operator =
                    x -> Pair.of(x.getRight(), x.getLeft().add(x.getRight()));

            return Stream.iterate(seed, operator)
                    .map(p -> p.getRight());
        }

    }

    public static class VAVR {

        public static io.vavr.collection.Stream<BigInteger> fibonacci() {
            return io.vavr.collection.Stream.of(BigInteger.ZERO, BigInteger.ONE)
                    .appendSelf(self -> self.zip(self.tail()).map(t -> t._1.add(t._2)));
        }

    }

    public static class Reactor {

        public static Flux<Long> fibonacci() {

            return Flux.generate(
                    () -> Tuples.of(1L, 2L),
                    (state, sink) -> {
                        sink.next(state.getT1());
                        return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                    }
            );
        }

    }

}