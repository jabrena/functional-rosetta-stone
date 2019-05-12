package org.fundamentals.fp.second;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NovelServiceTest {

    @Test
    public void getWinnersInPhysicsTest() {

        NovelService novelService = new NovelService();
        Long result = novelService.getWinnersInPhysics();

        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void getWinnersInPhysics2018Test() {

        NovelService novelService = new NovelService();
        Long result = novelService.getWinnersInPhysics2018();

        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void getFirstWinnersInPhysicsTest() {

        NovelService novelService = new NovelService();
        Long result = novelService.getFirstWinnersInPhysics();

        assertThat(result).isGreaterThan(0);
    }

}