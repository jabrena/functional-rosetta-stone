package info.jab.fp.async;

import java.util.concurrent.StructuredTaskScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
