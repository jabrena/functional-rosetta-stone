package info.jab.fp.euler.utils;

import org.junit.jupiter.api.BeforeEach;

public class BaseEulerProblemTest {

    protected AnswerLoader euler;

    @BeforeEach
    private void init() {
        euler = new AnswerLoader();
    }

}
