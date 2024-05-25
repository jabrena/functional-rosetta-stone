package info.jab.fp.cf;

import java.time.Duration;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

class FBasicsTest {

    @Test
    public void given_CF_when_Call_then_returnExpectedValue() {

        FBasics example = new FBasics();

        await()
                .atMost(Duration.ofSeconds(3))
                .until(example::myFirstF, equalTo(3));
    }

}