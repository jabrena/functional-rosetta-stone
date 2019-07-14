package org.fundamentals.fp.latency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class LatencyProblem01Benchmark {

    @State(Scope.Thread)
    public static class St {

        final int TIMEOUT = 2;

        ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });

        LatencyProblem01 problem = new LatencyProblem01(executor, TIMEOUT);

        final List<String> listOfGods = List.of(
                "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "http://my-json-server.typicode.com/jabrena/latency-problems/nordic",
                "http://my-json-server.typicode.com/jabrena/latency-problems/roman");

    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.listOfGods);
    }

}
