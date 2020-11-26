package com.example.badcode.dicegame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameTest {
    private final InputStream sysIn = System.in;
    private final PrintStream sysOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut = new ByteArrayOutputStream();

    private Game game;

    @Mock
    private Die firstDie;

    @Mock
    private Die secondDie;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(testOut));
        game = new Game(firstDie, secondDie);
        reset(firstDie, secondDie);
    }

    @AfterEach
    public void tearDown() {
        System.setIn(sysIn);
        System.setOut(sysOut);
    }

    private void userChoice(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void shouldDisplayUnrolledDiceValues() {
        game.displayDetails();
        assertThat(testOut.toString().contains("Dice 1: 0"), is(Boolean.TRUE));
        assertThat(testOut.toString().contains("Dice 2: 0"), is(Boolean.TRUE));
        assertThat(testOut.toString().contains("Score is: 0"), is(Boolean.TRUE));
    }

    @Test
    public void shouldDisplayRolledDiceValuesAndScore() {
        when(firstDie.getFaceValue()).thenReturn(1);
        when(secondDie.getFaceValue()).thenReturn(2);

        game.shoot();
        game.calculateScore();
        game.displayDetails();
        assertThat(testOut.toString(), containsString("Dice 1: 1"));
        assertThat(testOut.toString(), containsString("Dice 2: 2"));
        assertThat(testOut.toString(), containsString("Score is: 3"));
    }

    //TODO: 1 - Add a test to check a winning score shows the right result
    //TODO: 2 - Add a test to check a losing score shows the right result
    //TODO(stretch goal): 3 - Add a test to check the logic to play again works as expected on a draw

}
