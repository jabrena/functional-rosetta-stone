package org.fundamentals.fp.training.third;

import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Right;
import static io.vavr.Patterns.$Some;
import static io.vavr.Predicates.instanceOf;
import static org.assertj.core.api.Assertions.assertThat;

public class GodServiceTest {

    @Test
    public void searchGodsStartingWithH() {

        GodService godService = new GodService();
        java.util.List<String> godList = godService.searchGodsStartingWithH();

        godList.stream().forEach(god -> assertThat(god.toLowerCase().charAt(0)).isEqualTo('h'));
        assertThat(godList.size()).isEqualTo(3);

    }

    @Test
    public void searchNoGodsWithLargeNames() {

        GodService godService = new GodService();
        Optional<List<String>> list = godService.searchGodsWithLargeNames();

        assertThat(list.get().size()).isEqualTo(0);
    }

    @Test
    public void searchNoGodsWithLargeNames2() {

        GodService godService = new GodService();
        Option<List<String>> list = godService.searchGodsWithLargeNames2();

        List<String> response = Match(list).of(
                Case($Some($()), value -> value),
                Case($None(), new ArrayList<>()));

        assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void searchNoGodsWithLargeNames3() {

        GodService godService = new GodService();
        Either<Throwable, List<String>> list = godService.searchGodsWithLargeNames3();

        List<String> response = Match(list).of(
                Case($Right($()), value -> value),
                Case($Left($(instanceOf(RuntimeException.class))), new ArrayList<>())
        );

        assertThat(response.size()).isEqualTo(0);
    }

}
