package info.jab.fp.async;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.concurrent.TimeoutException;

public class LoomExamples {
    
    private static final Logger logger = LoggerFactory.getLogger(LoomExamples.class);

    public void usingVThread1() {
        try {
            // virtual thread
            var vthread = Thread.ofVirtual()
                .name("virtual-", 0)
                .start(() -> {
                    logger.info("virtual " + Thread.currentThread());
                });

            vthread.join();
        } catch (InterruptedException e) {

        }
    }

    private static final ThreadLocal<String> USER = new ThreadLocal<>();
  
    public String usingVThread2() {
        USER.set("White");
        try {
            var vthread = Thread.ofVirtual().start(() -> {
                USER.set("Black");
                logger.info("Hello " + USER.get());
                USER.remove();
                logger.info("Hello " + USER.get());
            });
            vthread.join();
        } catch (InterruptedException e) { }
        return USER.get();
    }

    //TODO Review ScopedValue in detail
    private static final ScopedValue<String> USER2 = ScopedValue.newInstance();

    public String usingVThread3() {
        
        ScopedValue.runWhere(USER2, "Black", () -> {});
        try {
            var vthread = Thread.ofVirtual()
            .start(() -> {
              ScopedValue.runWhere(USER2, "White", () -> {
                logger.info("Hello " + USER2.get());
              });
            });
            vthread.join();
        } catch (InterruptedException e) { }
        return USER2.get();
    }

    public Integer usingVThread4() {
        try (var scope = new StructuredTaskScope<Integer>()) {
            var task1 = scope.fork(() -> {
                Thread.sleep(1_000);
                return 1;
            });
            var task2 = scope.fork(() -> {
                Thread.sleep(1_000);
                return 2;
            });
            
            try {
                scope.join();
            } catch (InterruptedException e) { }

            var result = task1.get() + task2.get();
            return result;
        }
    }

    public State usingVThread5() {
        try (var scope = new StructuredTaskScope<Integer>()) {
            var task = scope.fork(() -> {
              Thread.sleep(1_000);
              return 42;
            });
            logger.info("{}",task.state());  // UNAVAILABLE
            try {
                scope.join();
            } catch (InterruptedException e) { }
            logger.info("{}",task.state());  // SUCCESS
            return task.state();
          }
    }

    public Integer usingVThread6() {
        var result = 0;
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Integer>()) {
            scope.fork(() -> {
              Thread.sleep(1_000);
              return 1;
            });
            scope.fork(() -> {
              Thread.sleep(42);
              return 2;
            });
            try {
                result = scope.join().result();
            } catch (InterruptedException | ExecutionException e) { }
        }
        return result;
    }

    public Integer usingVThread7() {
        var result = 0;
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> {
              Thread.sleep(1_000);
              return 1;
            });
            var task2 = scope.<String>fork(() -> {
              Thread.sleep(42);
              return "2";
            });
            try {
                scope.join().throwIfFailed();
            } catch (InterruptedException | ExecutionException ex) {}
            System.out.println(task1.get() + task2.get());
          }
        return result;
    }

    public Integer usingVThread8() {
        var result = 0;

        try (var scope = new StructuredTaskScope<>()) {
            var task1 = scope.fork(() -> {
                Thread.sleep(1_000); // throws InterruptedException
                return 1;
            });
            var task2 = scope.fork(() -> {
                Thread.sleep(5_000);  // throws InterruptedException
                return 2;
            });
            try {
                scope.joinUntil(Instant.now().plus(Duration.ofMillis(100)));
            } catch (TimeoutException | InterruptedException e) {
                //scope.shutdown();
            }
            System.out.println(task1.state());  // UNAVAILABLE
            System.out.println(task2.state());  // UNAVAILABLE
        }
        return result;
    }
}
