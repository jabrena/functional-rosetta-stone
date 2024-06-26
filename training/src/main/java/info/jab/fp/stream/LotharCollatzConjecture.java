package info.jab.fp.stream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotharCollatzConjecture {

    private static final Logger logger = LoggerFactory.getLogger(LotharCollatzConjecture.class);

    private final int initialNumber;
    private final AtomicInteger currentNumber = new AtomicInteger();

    public LotharCollatzConjecture(int initialNumber) {
        this.initialNumber = initialNumber;
    }

    private Integer nextNumber() {

        int value = currentNumber.intValue();
        int newValue;

        if(isEven(value)){
            newValue = value / 2;
        } else {
            newValue = (value * 3) + 1;
        }

        currentNumber.set(newValue);
        return currentNumber.get();
    }

    private boolean isEven(int value) {
        return value % 2 == 0;
    }

    public boolean verify() {

        logger.info("{}", this.initialNumber);
        Stream.generate(() -> this.nextNumber())
                .takeWhile(i -> i > 1)
                .forEach(i -> logger.info("{}", i));
        logger.info("{}", 1);
        return true;
    }

}
