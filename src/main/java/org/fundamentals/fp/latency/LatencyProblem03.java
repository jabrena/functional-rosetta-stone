package org.fundamentals.fp.latency;

import java.util.List;

public class LatencyProblem03 {

    private final List<String> listOfGods;

    public LatencyProblem03(List<String> listOfGods) {
        this.listOfGods = listOfGods;
    }

    public enum GODS {
        GREEK,
        ROMAN,
        NORDIC
    }

    public List<String> JavaStreamSolution(GODS type) {

        return List.of();

    }
}
