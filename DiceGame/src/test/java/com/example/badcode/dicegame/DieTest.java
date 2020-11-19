package com.example.badcode.dicegame;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DieTest {
    private Die die = new Die();

    @Test
    public void shouldReturnZeroIfNotRolled() {
        assertThat(die.getFaceValue(), is(0));
    }

    @Test
    public void shouldReturnNonZeroWhenRolled() {
        die.roll();
        assertThat(die.getFaceValue(), is(not(0)));
    }
}
