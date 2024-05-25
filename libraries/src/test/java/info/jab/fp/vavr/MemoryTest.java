package info.jab.fp.vavr;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.Function1;
import io.vavr.control.Option;
import io.vavr.control.Try;

//Idea from: https://github.com/cruftex/java-memory-benchmark/blob/master/src/test/java/MemoryBenchmarkTest.java
public class MemoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTest.class);

    Function1<String, Option<URL>> toURL3 = address ->
            Try.of(() -> new URL(address))
                    .map(u -> Option.some(u))
                    .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage()))
                    .recover(ex -> Option.none())
                    .get();

    URL parse(String addr) {
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
