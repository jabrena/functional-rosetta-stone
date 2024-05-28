package info.jab.fp.async;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoresDemo {

    private static final Logger logger = LoggerFactory.getLogger(CoresDemo.class);

    public void cores() {

        //Decorator Pattern
        Consumer<Supplier> stopWatch = process -> {

            logger.info("Starting the process");
            
            long startTime = System.currentTimeMillis();

            process.get();

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;

            logger.info("Computation time: {}", elapsedTime);
        };

        Supplier<Integer> process = () -> {

            var number = 1;
            var cores = Runtime.getRuntime().availableProcessors();

            logger.info("Number of cores: {}", cores);

            var clients = IntStream.rangeClosed(1, (cores - 1) * number).boxed()
                .map(Client::new)
                .toList();

            var futureRequests = clients.stream()
                .map(Client::runAsync)
                .toList();

            futureRequests.stream()
                .map(CompletableFuture::join)
                .toList();

            return 0;
        };

        stopWatch.accept(process);
    }

    static class Client {

        private int x;

        public Client(int x) {
            logger.info("new Instance: {}", x);
            this.x = x;
        }

        CompletableFuture<Integer> runAsync() {

            return CompletableFuture
                .supplyAsync(() -> longProcess())
                .orTimeout(60, TimeUnit.SECONDS)
                .handle((response, ex) -> {
                    if (!Objects.isNull(ex)) {
                        logger.error(ex.getLocalizedMessage(), ex);
                    }
                    return response;
                });
        }

        private Integer longProcess() {
            logger.info("Running: {}", this.x);
            sleep(2);
            return 0;
        }

        private void sleep(int seconds) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                //Empty
            }
        }
    }
}
