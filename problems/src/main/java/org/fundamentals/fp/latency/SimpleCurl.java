package org.fundamentals.fp.latency;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Option;
import io.vavr.control.Try;

public class SimpleCurl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCurl.class);

    static Function<URL, String> fetch = url -> Try.of(() -> {

        LOGGER.debug("Thread: {}", Thread.currentThread().getName());
        LOGGER.debug("Requested URL: {}", url);

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
        throw new RuntimeException("Bad Request", ex);
    });


    static Function<URL, Option<String>> fetch2 = url -> Try.of(() -> {

        LOGGER.debug("Thread: {}", Thread.currentThread().getName());
        LOGGER.debug("Requested URL: {}", url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url.toURI())
                .build();

        return Option.some(client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body());
    })
    .onFailure(ex -> LOGGER.error("SimpleCURL Error: {}", ex.getLocalizedMessage(), ex))
    .recover(ex -> Option.none())
    .get();

    static Function<String, String> log = value -> {
        LOGGER.debug("Response: {}", value);
        return value;
    };
}