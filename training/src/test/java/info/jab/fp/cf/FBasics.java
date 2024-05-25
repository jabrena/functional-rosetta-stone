package info.jab.fp.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class FBasics {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    Function<Integer, Integer> compute = param -> {

        System.out.println("Compute");

        Delay(2);

        //Passing Ball with delay
        return param;
    };

    public Integer myFirstF() {
        try {
            return asyncCall().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Katakrocker", e);
        }
    }

    public Future<Integer> asyncCall() {
        return executor.submit(() -> compute.apply(3));
    }

    private static void Delay(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
