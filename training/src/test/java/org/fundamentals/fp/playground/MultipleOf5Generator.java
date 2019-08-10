package org.fundamentals.fp.playground;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.GeneratorConfiguration;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class MultipleOf5Generator extends Generator<Integer> {

    public MultipleOf5Generator() {
        super(Integer.class);
    }

    @Override
    public Integer generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        return sourceOfRandomness.nextInt(Integer.MIN_VALUE / 5, Integer.MAX_VALUE / 5) * 5;
    }

    @Retention(RUNTIME)
    @GeneratorConfiguration
    @From(MultipleOf5Generator.class)
    public @interface MultipleOf5 {
    }
}