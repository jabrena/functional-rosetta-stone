package org.fundamentals.fp.euler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.iterate;
import static java.util.stream.LongStream.rangeClosed;

public class PrimeFactors {

   public static IntStream primeFactors(int n) {
      return IntStream.range(2, n-1)
              .filter(i -> n % i == 0 &&
                      !primeFactors(i).findAny().isPresent()); // or primeFactors(i).count() == 0
   }

   public static List<Integer> primeFactors2(int n) {
      ObjIntConsumer<ArrayList<Integer>> accumulator = (list, i) -> {
         if(n % i == 0 && list.stream().allMatch(p -> i % p != 0))
            list.add(i);
      };
      return IntStream.range(2, n - 1).collect(ArrayList::new, accumulator,
              (list1, list2) -> list2.forEach(i -> accumulator.accept(list1, i)));
   }

   public static List<Long> sieveOfEratosthenes(int n) {
      boolean[] notPrime = new boolean[n + 1];

      rangeClosed(2, (int) Math.sqrt(n))
              .filter(x -> !notPrime[(int)x])
              .flatMap(x -> iterate(x * x, m -> m <= n, m -> m + x))
              .forEach(x -> notPrime[(int)x] = true);

      return rangeClosed(2, n)
              .filter(x -> !notPrime[(int)x])
              .boxed().collect(toList());
   }

   public static void main(String args[]){
      int number;

      number = 13195;
      
      for(int i = 2; i< number; i++) {
         System.out.println(i + " " + number + " " + number % i);
         while(number % i == 0) {
            number = number/i;
            System.out.println(i + " " + number);
         }
      }
      if(number >2) {
         System.out.println(number);
      }

   }
}