package org.fundamentals.fp.playground.streams;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotharCollatzConjecture {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotharCollatzConjecture.class);

    final private int initialNumber;
    private AtomicInteger currentNumber = new AtomicInteger();

    LotharCollatzConjecture(int initialNumber) {
        this.initialNumber = initialNumber;
        currentNumber.set(initialNumber);
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

        LOGGER.info("{}", this.initialNumber);
        Stream.generate(() -> this.nextNumber())
                .takeWhile(i -> i > 1)
                .forEach(i -> LOGGER.info("{}", i));
        LOGGER.info("{}", 1);

        return true;

    }

}
