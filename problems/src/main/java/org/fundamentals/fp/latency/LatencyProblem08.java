package org.fundamentals.fp.latency;

import io.vavr.control.Option;
import java.util.List;
import java.util.concurrent.Executor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Feature: Consume a REST Indian God Service
 *
 * Scenario: Consume the API in a Happy path case
 *     Given a REST API about Indian gods
 *     When  the client sends the request
 *     And   execute a Rate limiter Policy
 *     Then  return all gods who contains in the name `a` & `i`
 *
 * Scenario: Force a Rate limiter behaviour
 *     Given a REST API about Indian gods
 *     When  the client sends the request
 *     And   execute a Rate limiter Policy
 *     Then  return all gods who contains in the name `a` & `i`
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem08 {

    @Data
    @AllArgsConstructor
    public static class Config {

        private String address;
        private Executor executor;
        private int timeout;
    }

    @NonNull
    private final LatencyProblem08.Config config;

    public Option<List<String>> JavaStreamSolution() {

        return Option.none();
    }

}
