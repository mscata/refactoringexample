package com.example.badcode.dicegame;

import java.util.function.Supplier;

public class GeneratorDie implements Rollable {
    private final Supplier<Integer> generator;

    public GeneratorDie(final Supplier<Integer> generator) {
        this.generator = generator;
    }

    @Override
    public int roll() {
        return generator.get();
    }
}
