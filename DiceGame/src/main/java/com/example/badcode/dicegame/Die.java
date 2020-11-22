package com.example.badcode.dicegame;

import java.util.Random;

public class Die implements Rollable {
    int faceValue;

    public int roll() {
        final Random rand = new Random();
        faceValue = rand.nextInt(6) + 1;
        return faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }
}
