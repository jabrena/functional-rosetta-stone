package org.fundamentals.fp.playground;

import io.reactivex.annotations.Nullable;
import io.vavr.Function1;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

//Idea from: https://github.com/cruftex/java-memory-benchmark/blob/master/src/test/java/MemoryBenchmarkTest.java
@Slf4j
public class MemoryTest {

    Function1<String, Option<URL>> toURL3 = address ->
            Try.of(() -> new URL(address))
                    .map(u -> Option.some(u))
                    .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage()))
                    .recover(ex -> Option.none())
                    .get();

    @Nullable URL parse(String addr) {
        try {
            return new URL(addr);
        } catch(Exception e) {
            LOGGER.warn(e.getLocalizedMessage());
            return null;
        }
    }

    @Test
    public void test_given_okAddress_executeFunction_then_receiveExpectedBehaviour() {

        String address = "http://as.com";
        toURL3.apply(address);
        Util.printUsedMemory();
    }

    @Test
    public void test_given_okAddress_executeParse_then_receiveExpectedBehaviour() {

        String address = "http://as.com";
        parse(address);
        Util.printUsedMemory();
    }

    @Test
    public void test_given_badAddress_executeFunction_then_receiveExpectedBehaviour() {

        String address = "as.com";
        toURL3.apply(address);
        Util.printUsedMemory();
    }

    @Test
    public void test_given_badAddress_executeParse_then_receiveExpectedBehaviour() {

        String address = "as.com";
        parse(address);
        Util.printUsedMemory();
    }
}
