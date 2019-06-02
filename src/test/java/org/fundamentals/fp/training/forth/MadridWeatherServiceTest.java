package org.fundamentals.fp.training.forth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MadridWeatherServiceTest {

    @Test
    public void getAverageTest() {

        MadridWeatherService madridWeatherService = new MadridWeatherService();
        assertThat(madridWeatherService.getAverage()).isGreaterThan(0);
    }

    @Test
    public void getAverage2Test() {

        MadridWeatherService madridWeatherService = new MadridWeatherService();
        assertThat(madridWeatherService.getAverage2()).isGreaterThan(0);
    }

}