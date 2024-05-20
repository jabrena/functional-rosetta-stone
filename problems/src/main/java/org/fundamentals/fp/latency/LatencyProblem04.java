package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerType3;
import reactor.core.publisher.Mono;

import static org.fundamentals.fp.latency.SimpleCurl.fetch;
import static org.fundamentals.fp.latency.SimpleCurl.log;

/**
 *
 * Given a set of providers to exchange money
 * When  make the request to get the rate for the exchange 100 EUR into USD
 * Then  find the average rate from valid responses from the the providers
 *
 * https://transferwise.com/fr?sourceCurrency=EUR&targetCurrency=USD&sourceAmount=100
 * https://www.xe.com/es/currencyconverter/convert/?Amount=100&From=EUR&To=USD
 * https://www.iban.com/currency-converter?from_currency=EUR&to_currency=USD&amount=100
 * https://www.x-rates.com/calculator/?from=EUR&to=USD&amount=100
 *
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem04 implements IEulerType3<BigDecimal> {

    @Data
    @AllArgsConstructor
    public static class Config {

        private List<String> list;
        private Executor executor;
        private int timeout;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExchangeRate {

        private long epoc;
        private String from;
        private BigDecimal amount;
        private String to;
        private BigDecimal rate;
    }

    private final Config config;

    @Override
    public BigDecimal JavaSolution() {
        return null;
    }

    Function<String, URL> toURLOld = address -> {
        try {
            return new URL(address);
        } catch (MalformedURLException ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new RuntimeException("Bad address", ex);
        }
    };

    Function1<String, URL> toURL = address -> Try.of(() -> new URL(address))
        .getOrElseThrow(ex -> {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new RuntimeException("Bad address", ex);
    });

    CheckedFunction1<String, URL> toURL2 = address -> new URL(address);

    Function1<String, Option<URL>> toURL3 = address ->
            Try.of(() -> new URL(address))
                .map(u -> Option.some(u))
                .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();

    Function1<Option<URL>, Optional<URL>> toOptional = option -> option.toJavaOptional();

    Function2<URL, Config, CompletableFuture<String>> fetchAsync = (address, config) -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.andThen(log).apply(address), config.getExecutor())
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("[\"FETCH_BAD_RESULT_TIMEOUT\"]", config.getTimeout(), TimeUnit.SECONDS);
    };

    Function<String, ExchangeRate> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeRate deserializedData = objectMapper.readValue(param, new TypeReference<ExchangeRate>() {});
        return deserializedData;
    }).getOrElseThrow(ex -> {
        LOGGER.error("Bad Serialization process", ex);
        throw new RuntimeException(ex);
    });

    @Override
    public BigDecimal JavaStreamSolution() {

        Stream<CompletableFuture<String>> requests = config.getList().stream()
                .map(toURL3)
                .filter(Option::isDefined)
                .map(o -> fetchAsync.apply(o.get(), config));

        return BigDecimal.valueOf(requests
                .map(CompletableFuture::join)
                .map(serialize)
                .map(er -> er.getRate())
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .getAsDouble());
    }

    @Override
    public BigDecimal VAVRSolution() {
        return null;
    }

    @Override
    public Mono<BigDecimal> ReactorSolution() {
        return null;
    }

    @Override
    public Single<BigDecimal> RxJavaSolution() {
        return null;
    }

}


