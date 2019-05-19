package org.fundamentals.fp.third;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GodServiceTest {

    @Test
    public void searchGodsStartingWithH() {

        GodService godService = new GodService();
        List<String> godList = godService.searchGodsStartingWithH();

        godList.stream().forEach(god -> assertThat(god.toLowerCase().charAt(0)).isEqualTo('h'));
        assertThat(godList.size()).isEqualTo(3);

    }

}
