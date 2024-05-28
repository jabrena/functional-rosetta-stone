package info.jab.fp.async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.utils.TestLoggerExtension;

@ExtendWith(TestLoggerExtension.class)
public class CoresDemoTest {

    @Test
    public void should_run() {

        //Given
        var cores = new CoresDemo();

        //When
        //Then
        cores.cores();
    }

}