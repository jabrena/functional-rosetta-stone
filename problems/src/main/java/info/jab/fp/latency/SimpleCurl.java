package info.jab.fp.latency;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCurl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCurl.class);

    static Function<URL, String> fetch = url -> {
        try {
            LOGGER.debug("Thread: {}", Thread.currentThread().getName());
            LOGGER.debug("Requested URL: {}", url);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url.toURI())
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new RuntimeException("Bad Request", ex);
        }
    };


    static Function<URL, Optional<String>> fetch2 = url -> {
        try {
            LOGGER.debug("Thread: {}", Thread.currentThread().getName());
            LOGGER.debug("Requested URL: {}", url);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url.toURI())
                    .build();

            String responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return Optional.of(responseBody);
        } catch (Exception ex) {
            LOGGER.error("SimpleCURL Error: {}", ex.getLocalizedMessage(), ex);
            return Optional.empty();
        }
    };

    static Function<String, String> log = value -> {
        LOGGER.debug("Response: {}", value);
        return value;
    };
}