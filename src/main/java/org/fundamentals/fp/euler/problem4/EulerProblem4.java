package org.fundamentals.fp.euler.problem4;

import io.vavr.Function1;
import io.vavr.collection.List;
import java.util.function.Predicate;

public class EulerProblem4 {

    public Long javaSolution(int min, int max) {
        
        return null;
    }

    public Long javaStreamSolution(int min, int max) {

        return null;
    }


    public Integer VAVRSolution(int min, int max) {

         Function1<Integer, String> reverse = number -> new StringBuilder().append(number).reverse().toString();
         Predicate<Integer> isPalindrome = element -> element.toString().equals(reverse.apply(element));

         return List.rangeClosed(min, max)
                .crossProduct()
                .filter(t -> t._1 <= t._2)
                .map(t -> t._1 * t._2)
                .filter(isPalindrome)
                .max()
                .get();
    }
}
