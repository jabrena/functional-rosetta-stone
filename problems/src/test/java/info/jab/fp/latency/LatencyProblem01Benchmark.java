package info.jab.fp.latency;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

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

}
