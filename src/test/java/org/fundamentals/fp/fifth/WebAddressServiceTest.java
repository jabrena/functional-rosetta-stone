package org.fundamentals.fp.fifth;

import io.vavr.Function1;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WebAddressServiceTest {

    @Test
    public void getValidAddressesTest() {

        WebAddressService webAddressService = new WebAddressService();
        List<String> list = webAddressService.search();

        assertThat(list.size()).isGreaterThan(0);

        Function1<String, String> toUpper = String::toUpperCase;
        Function1<String, String> trim = String::trim;
        Function1<String, String> cheers = (s) -> String.format("Hello %s", s);

        assertThat(trim
                .andThen(toUpper)
                .andThen(cheers)
                .apply("   john")).isEqualTo("Hello JOHN");
    }

}