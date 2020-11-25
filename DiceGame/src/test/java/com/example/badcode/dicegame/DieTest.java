package com.example.badcode.dicegame;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

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

    //TODO: 1 - Add a test to this class that tests the upper bound of the die roll.
    //TODO: 2 - What are the problems with this test?  Make notes to share with the group any observations.
    //TODO: 3 - What is the test actually testing?  Make notes to share with the group any observations.
}
