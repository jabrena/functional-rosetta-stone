package info.jab.fp.vavr;

import io.vavr.Function1;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class VAVRFeatureTest {

    @Test
    public void functionalCompositionWithVAVRTest() {

        Function1<String, String> toUpper = String::toUpperCase;
        Function1<String, String> trim = String::trim;
        Function1<String, String> cheers = (s) -> String.format("Hello %s", s);

        assertThat(trim
                .andThen(toUpper)
                .andThen(cheers)
                .apply("   john"))
                .isEqualTo("Hello JOHN");
    }

}
