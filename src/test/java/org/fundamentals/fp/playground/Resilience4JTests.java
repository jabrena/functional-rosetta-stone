package org.fundamentals.fp.playground;

//import com.codahale.metrics.MetricRegistry;
//import com.codahale.metrics.SharedMetricRegistries;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import javax.naming.InvalidNameException;
//import javax.ws.rs.WebApplicationException;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.consumer.CircularEventConsumer;
import io.github.resilience4j.metrics.CircuitBreakerMetrics;
import io.github.resilience4j.metrics.Timer;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.retry.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.API;
import io.vavr.CheckedFunction1;
import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.anyOf;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;
import static io.vavr.Predicates.noneOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class Resilience4JTests {

    @Test
    public void testExecuteAround(){
        HelloWorldService service = null;

        Function1<String, String> supplier = withRecovery(
                (input) -> service.sayHelloWorld(input),
                ex -> logException(ex),
                (input) -> "Hallo an " + input);
        String result = supplier.apply("JUG Darmstadt");

        LOGGER.info("{}", result);

        CheckedFunction1<String, String> function = (String input) -> service.sayHelloWorld(input);

        Function1<String, String> recoveryFunction = function.recover(ex -> {
            logException(ex);
            return (input) -> "Hallo an " + input;
        });
        String result2 = recoveryFunction.apply("JUG Darmstadt");

        LOGGER.info("{}", result2);
    }

    private <T, R> Function1<T, R> withRecovery(
            CheckedFunction1<T, R> function,
            Consumer<Throwable> exceptionHandler,
            Function1<T, R> recoverFunction) {

        return (T t) -> {
            try {
                return function.apply(t);
            } catch (Throwable ex) {
                exceptionHandler.accept(ex);
                return recoverFunction.apply(t);
            }
        };
    }

    private void logException(Throwable e) {
        LOGGER.error("Error: {}", e.getLocalizedMessage(), e);
    }


    // Service Interface mit Checked exception
    public interface HelloWorldService {
        String sayHelloWorld(String name) throws BusinessException;
        //Try<String> sayHelloWorld(String name);
    }

    public void tryExample(){
        HelloWorldService helloWorldService = null;

        String result = Try.of(() -> helloWorldService.sayHelloWorld("Robert"))
                .map(value -> value + " und allen Anwesenden")
                .recover(BusinessException.class, throwable -> "Ich muss weg!")
                .onFailure(throwable -> LOGGER.warn("Handled exception", throwable))
                .getOrElse(() -> "Ich muss auch weg");
    }

    @Test
    public void eitherRightExample(){
        Either<Exception, String> either = Either.right("Hello world");

        // = "HELLO WORLD"
        String result = either
                .map(String::toUpperCase)
                .get();

        then(result).isEqualTo("HELLO WORLD");

        Try<String> toTry = either.toTry();
        Option<String> toOption = either.toOption();

        String helloWorld = Option.of("Hello")
                .map(value -> value + " world")
                .peek(value -> LOGGER.info("Value: {}", value))
                .getOrElse(() -> "Ich muss weg!");

        then(helloWorld).isEqualTo("Hello world");
    }

    @Test
    public void tryDivide(){
        int result = divide(8,2).get();

        assertThat(result).isEqualTo(7);
    }

    @Test
    public void tryDivideFailure(){
        int result = divide(8,0).get();

        assertThat(result).isEqualTo(8);
    }

    public Try<Integer> divide(int dividend, int divisor) {
        return Try.of(() -> dividend / divisor)
            .map(quotient -> quotient + 3)
            .recoverWith(ex -> Try.success(dividend));
    }


    public void lazyExample(){
        Lazy<Double> lazy = Lazy.of(Math::random);
        lazy.isEvaluated(); // = false
        lazy.get();         // = 0.123 (random generated)
        lazy.isEvaluated(); // = true
        lazy.get();         // = 0.123 (cached)
    }

    public String patternMatching(Integer i){
        return Match(i).of(
                Case($(1), () -> "1"),
                Case($(isIn(2, 3)), () ->  "2 or 3"),
                Case($(anyOf(is(4), noneOf(is(5), is(6)))), () ->  "4 or not (5 or 6)"),
                Case($(), () -> "?")
                );
    }

    /*
    public void circuitBreaker() throws Exception {

        HelloWorldService service = null;

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .recordFailure(throwable -> Match(throwable).of(
                        Case($(instanceOf(IOException.class)), true),
                        Case($(instanceOf(WebApplicationException.class)),
                                ex -> ex.getResponse().getStatus() == INTERNAL_SERVER_ERROR.getStatusCode()),
                        Case($(), false)))
                .build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("testName", circuitBreakerConfig);

        Callable<String> decorateCallable = CircuitBreaker
                .decorateCallable(circuitBreaker, () -> service.sayHelloWorld("Robert"));
        String result = Try.ofCallable(decorateCallable)
            .getOrElse("Ich muss weg!");

        result = circuitBreaker.executeCallable(() -> service.sayHelloWorld("Robert")) ;

    }

    public void metrics() throws Exception {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreaker circuitBreaker = registry.circuitBreaker("backendName");

        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        float failureRate = metrics.getFailureRate();
        int bufferedCalls = metrics.getNumberOfBufferedCalls();
        int failedCalls = metrics.getNumberOfFailedCalls();
        int successfulCalls = metrics.getNumberOfSuccessfulCalls();
        long notPermittedCalls = metrics.getNumberOfNotPermittedCalls();

        MetricRegistry metricRegistry = SharedMetricRegistries.getOrCreate("myRegistry");
        metricRegistry.registerAll(CircuitBreakerMetrics.ofCircuitBreakerRegistry(registry));
    }

    public void events() throws Exception {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("name1");
        circuitBreaker.getEventPublisher()
            .onSuccess(event -> LOG.debug(event.toString()))
            .onError(event -> LOG.warn(event.toString()))
            .onStateTransition(event -> LOG.info(event.toString()));

        RxJava2Adapter.toFlowable(circuitBreaker.getEventPublisher())
                .filter(event -> event.getEventType() == Type.ERROR)
                .cast(CircuitBreakerOnErrorEvent.class)
                .subscribe(event -> LOG.warn(event.toString()));




        CircularEventConsumer<CircuitBreakerEvent> ringBuffer = new CircularEventConsumer<>(10);
        circuitBreaker.getEventPublisher().onEvent(ringBuffer);
        List<CircuitBreakerEvent> bufferedEvents = ringBuffer.getBufferedEvents();
    }
    */

    @Disabled
    @Test
    public void rateLimiter() throws Exception {
        HelloWorldService service = null;

        RateLimiterConfig config = RateLimiterConfig.custom()
            .timeoutDuration(Duration.ofMillis(100))
            .limitRefreshPeriod(Duration.ofSeconds(1))
            .limitForPeriod(1)
            .build();
        RateLimiter rateLimiter = RateLimiter.of("backendName", config);

        Callable<String> decorateCallable = RateLimiter
                .decorateCallable(rateLimiter, () -> service.sayHelloWorld("Robert"));

        Try<String> firstTry = Try.ofCallable(decorateCallable);
        then(firstTry.isSuccess()).isTrue();
        Try<String> secondTry = Try.ofCallable(decorateCallable);
        then(secondTry.isFailure()).isTrue();
        then(secondTry.getCause()).isInstanceOf(RequestNotPermitted.class);

    }

    @Disabled
    @Test
    public void bulkhead() throws Exception {
        HelloWorldService service = null;

        BulkheadConfig config = BulkheadConfig.custom()
                .maxWaitTime(1000)
                .maxConcurrentCalls(10)
                .build();

        Bulkhead bulkhead = Bulkhead.of("bar", config);

        Callable<String> decorateCallable = Bulkhead
                .decorateCallable(bulkhead, () -> service.sayHelloWorld("Robert"));

        Try<String> firstTry = Try.ofCallable(decorateCallable);
        then(firstTry.isSuccess()).isTrue();
        Try<String> secondTry = Try.ofCallable(decorateCallable);
        then(secondTry.isFailure()).isTrue();
        then(secondTry.getCause()).isInstanceOf(BulkheadFullException.class);

    }

    @Test
    public void retry() {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

        HelloWorldService service = null;

        RetryConfig config = RetryConfig.custom()
            .maxAttempts(2)
            .waitDuration(Duration.ofMillis(100))
            .intervalFunction(IntervalFunction.ofExponentialBackoff())
            .retryOnException(throwable -> API.Match(throwable).of(
                Case($(instanceOf(IOException.class)), true),
                Case($(), false)))
            .build();
        Retry retry = Retry.of("backendName", config);

        Callable<String> decorateCallable = CircuitBreaker
                .decorateCallable(circuitBreaker, () -> service.sayHelloWorld("Robert"));
        decorateCallable = Retry.decorateCallable(retry, decorateCallable);
        String result = Try.ofCallable(decorateCallable)
                .getOrElse("Ich muss weg!");

        then(result).isEqualTo("Ich muss weg!");
    }

    /*
    public void timer(){
        HelloWorldService service = null;

        MetricRegistry metricRegistry = new MetricRegistry();
        Timer timer = Timer.ofMetricRegistry("backend", metricRegistry);
        Callable<String> decorateCallable = Timer
                .decorateCallable(timer, () -> service.sayHelloWorld("Robert"));

        Timer.Metrics metrics = timer.getMetrics();
        long totalCalls = metrics.getNumberOfTotalCalls();
        long successfulCalls = metrics.getNumberOfSuccessfulCalls();
        long failedCalls = metrics.getNumberOfFailedCalls();
        double meanRate = metrics.getMeanRate();
        double responseTimePercentile = metrics.getSnapshot().get95thPercentile();

    }
    */

    @Test
    public void matchUser(){
        User user = new User("Hack0r", "User01");
        /*
        Validation<Exception, String> result = Match(user).of(
                Case($User($("Hack0r"), $()), (name, id)
                        -> Validation.invalid(new InvalidNameException())),
                Case($(), () -> Validation.valid(user.getId())));
                */
    }

    private Either<Exception, List<User>> addUser(List<User> users, User user) {
        Validation<Exception, User> validation = validateUser(user);
        return validation
                .map(users::prepend)
                .toEither();
    }

    private Validation<Exception, User> validateUser(User user) {
        if(user != null && user.getName().equals("Hack0r")){
            return Validation.invalid(new InvalidNameException(user.getId()));
        }
        return Validation.valid(user);
    }
}
