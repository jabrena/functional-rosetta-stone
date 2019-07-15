package org.fundamentals.fp.latency;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/roman",
                "http://localhost:8090/nordic");

        LatencyProblem01 problem = new LatencyProblem01(listOfGods, executor, TIMEOUT);
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        assertThat(st.problem.JavaStreamSolution()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Benchmark
    public void ReactorSolution(St st) {

        StepVerifier
                .create(st.problem.ReactorSolution())
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }

    @Benchmark
    public void ReactorSolutionFunctionalComposition(St st) {

        StepVerifier
                .create(st.problem.ReactorSolutionFunctionalComposition())
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }

    @Benchmark
    public void ReactorSolutionAsync(St st) {

        StepVerifier
                .create(st.problem.ReactorSolutionAsync())
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }

    @Benchmark
    public void ReactorSolutionParallel(St st) {

        StepVerifier
                .create(st.problem.ReactorSolutionParallel())
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }

    @Benchmark
    public void ReactorSolutionSequential(St st) {

        StepVerifier
                .create(st.problem.ReactorSolutionSequential())
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }


}
