package info.jab.fp.async;

import java.util.concurrent.CompletableFuture;

public class CFHandle {

    public static void main(String[] args) {
        runTasks(2);
        runTasks(0);
    }

    private static void runTasks(int i) {

          System.out.printf("-- input: %s --%n", i);

          CompletableFuture
                  .supplyAsync(() -> {
                      return 16 / i;
                  })
                  /*
                  .handle((input, exception) -> {
                      if (exception != null) {
                          System.out.println("exception block");
                          System.err.println(exception);
                          return 1;
                      } else {
                          System.out.println("increasing input by 2");
                          return input + 2;
                      }})
                   */
                  .thenApply(input -> input * 3)
                  .thenApply(input -> input / 0)
                  .handle((input, exception) -> {
                      if (exception != null) {
                          System.out.println("exception block");
                          System.err.println(exception);
                          return 1;
                      } else {
                          System.out.println("increasing input by 2");
                          return input + 2;
                      }})
                  .thenAccept(System.out::println);
    }
}