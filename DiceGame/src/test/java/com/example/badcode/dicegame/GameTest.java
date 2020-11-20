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

    @Test
    public void shouldDecideWinningScore() {
        when(firstDie.getFaceValue()).thenReturn(3);
        when(secondDie.getFaceValue()).thenReturn(4);

        game.shoot();
        game.calculateScore();
        game.displayDetails();
        game.decideOutcome();

        assertThat(testOut.toString(), containsString("Congrats, this was a winning throw."));
    }

    @Test
    public void shouldDecideLosingScore() {
        when(firstDie.getFaceValue()).thenReturn(1);
        when(secondDie.getFaceValue()).thenReturn(2);

        game.shoot();
        game.calculateScore();
        game.displayDetails();
        game.decideOutcome();

        assertThat(testOut.toString(), containsString("Sorry, this was a losing throw."));
    }

    @Test
    public void shouldDecideDrawScoreAndChooseWrongOptionAndPlayAgainOnce() {
        when(firstDie.getFaceValue()).thenReturn(2);
        when(secondDie.getFaceValue()).thenReturn(3);

        userChoice("9\n1\n2");
        game = new Game(firstDie, secondDie);
        game.shoot();
        game.calculateScore();
        game.displayDetails();
        game.decideOutcome();

        assertThat(testOut.toString(), containsString("Incorrect choice, choose 1 or 2:"));
        assertThat(testOut.toString(), containsString("Draw, you get another try. Type 1 to throw again or 2 to quit:"));
        assertThat(testOut.toString(), containsString("Thank you for playing."));
        verify(firstDie, times(4)).getFaceValue();
        verify(secondDie, times(4)).getFaceValue();
    }
}
