package org.fundamentals.fp.euler;

import io.reactivex.Single;
import io.vavr.Function3;
import io.vavr.collection.Seq;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import reactor.core.publisher.Mono;

/**
 * Problem 8: Largest product in a series
 * https://projecteuler.net/problem=8
 *
 * The four adjacent digits in the 1000-digit number that
 * have the greatest product are 9 × 9 × 8 × 9 = 5832.
 *
 * Find the thirteen adjacent digits in the 1000-digit number that
 * have the greatest product. What is the value of this product?
 */
public class EulerProblem08 implements IEulerType1<Integer, Long> {

    private final String DATASOURCE =
            "73167176531330624919225119674426574742355349194934" +
            "96983520312774506326239578318016984801869478851843" +
            "85861560789112949495459501737958331952853208805511" +
            "12540698747158523863050715693290963295227443043557" +
            "66896648950445244523161731856403098711121722383113" +
            "62229893423380308135336276614282806444486645238749" +
            "30358907296290491560440772390713810515859307960866" +
            "70172427121883998797908792274921901699720888093776" +
            "65727333001053367881220235421809751254540594752243" +
            "52584907711670556013604839586446706324415722155397" +
            "53697817977846174064955149290862569321978468622482" +
            "83972241375657056057490261407972968652414535100474" +
            "82166370484403199890008895243450658541227588666881" +
            "16427171479924442928230863465674813919123162824586" +
            "17866458359124566529476545682848912883142607690042" +
            "24219022671055626321111109370544217506941658960408" +
            "07198403850962455444362981230987879927244284909188" +
            "84580156166097919133875499200524063689912560717606" +
            "05886116467109405077541002256983155200055935729725" +
            "71636269561882670428252483600823257530420752963450";

    @Override
    public Long JavaSolution(Integer limit) {

        List<Long> list = new ArrayList<>();
        for (int i = 0; i < DATASOURCE.length() - limit; i ++) {
            long current = 1;

            for (int j = i; j < i + limit; j++) {
                current *= Long.parseLong(DATASOURCE.substring(j, j + 1));
            }

            list.add(current);
        }

        return list.stream().sorted(Comparator.reverseOrder()).findFirst().get();
    }

    Function3<String, Integer, Integer, Long> calculateNumber = (data, i, limit) ->
            IntStream.range(i, i + limit).boxed()
                .map(j -> Long.parseLong(data.substring(j, j + 1)))
                .reduce(1L, (l1, l2) -> l1 * l2);

    @Override
    public Long JavaStreamSolution(Integer limit) {

        return IntStream.rangeClosed(0, DATASOURCE.length() - limit).boxed()
                .mapToLong(i -> calculateNumber.apply(DATASOURCE, i, limit))
                .max().getAsLong();
    }

    private static io.vavr.collection.List<Integer> digits(String num) {

        return io.vavr.collection.List.of(num.split(""))
                .map(s -> Character.digit(s.charAt(0), 10));
    }

    @Override
    public Long VAVRSolution(Integer limit) {

        return digits(DATASOURCE)
                .sliding(limit)
                .map(Seq::product)
                .max().get().longValue();
    }

    @Override
    public Mono<Long> ReactorSolution(Integer limit) {
        return null;
    }

    @Override
    public Single<Long> RxJavaSolution(Integer limit) {
        return null;
    }

    @Override
    public Long KotlinSolution(Integer limit) {
        return null;
    }
}
