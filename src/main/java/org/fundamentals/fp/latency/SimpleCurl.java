package org.fundamentals.fp.latency;

import io.vavr.control.Try;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCurl {

    static Function<URL, String> fetch = url -> Try.of(() -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        LOGGER.info("Requested URL: {}", url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url.toURI())
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }).getOrElseThrow(ex -> {
        LOGGER.error(ex.getLocalizedMessage(), ex);
        throw new RuntimeException("Bad Request");
    });
}
