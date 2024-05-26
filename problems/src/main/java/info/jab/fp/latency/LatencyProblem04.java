package info.jab.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.jab.fp.euler.IEulerType3;

import static info.jab.fp.latency.SimpleCurl.fetch;
import static info.jab.fp.latency.SimpleCurl.log;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
public class LatencyProblem04 implements IEulerType3<BigDecimal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem04.class);

    public static record Config(List<String> list,Executor executor, int timeout) {};

    public static record ExchangeRate(
        long epoc,
        String from,
        BigDecimal amount,
        String to,
        BigDecimal rate
        ){}

    private final Config config;

    public LatencyProblem04(info.jab.fp.latency.LatencyProblem04.Config config) {
        this.config = config;
    }

    @Override
    public BigDecimal JavaSolution() {
        return null;
    }

    Function<String, Optional<URL>> toURL3 = address -> {
        try {
            URL url = new URL(address);
            return Optional.of(url);
        } catch (MalformedURLException ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            return Optional.empty();
        }
    };

    BiFunction<URL, Config, CompletableFuture<String>> fetchAsync = (address, config) -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.andThen(log).apply(address), config.executor())
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("[\"FETCH_BAD_RESULT_TIMEOUT\"]", config.timeout(), TimeUnit.SECONDS);
    };

    Function<String, ExchangeRate> serialize = param -> {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(param, new TypeReference<ExchangeRate>() {});
        } catch (Exception ex) {
            LOGGER.error("Bad Serialization process", ex);
            throw new RuntimeException("Bad Serialization process", ex);
        }
    };

    @Override
    public BigDecimal JavaStreamSolution() {

        Stream<CompletableFuture<String>> requests = config.list().stream()
                .map(toURL3)
                .filter(Optional::isPresent)
                .map(o -> fetchAsync.apply(o.get(), config));

        return BigDecimal.valueOf(requests
                .map(CompletableFuture::join)
                .map(serialize)
                .map(er -> er.rate())
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .getAsDouble());
    }

}


