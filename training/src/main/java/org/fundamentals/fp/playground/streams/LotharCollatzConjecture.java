package org.fundamentals.fp.playground.streams;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Slf4j
public class LotharCollatzConjecture {

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
