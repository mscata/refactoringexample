package com.example.badcode.dicegame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScoreHandlerTest {
    private ScoreHandler decider = new ScoreHandler(Outcome.WIN, 2, 3, 4);
    private List<Integer> scoreRange = IntStream.range(2, 13).boxed().collect(Collectors.toList()); // upper boundary excluded
    private List<Integer> otherScores = new ArrayList<>(scoreRange); // clone the score range, then modify it

    public ScoreHandlerTest() {
        otherScores.removeAll(decider.getMatchingScores());
    }

    @Test
    public void shouldApplyToWinningScores() {
        decider.getMatchingScores().forEach(score -> {
            assertThat("Score: " + score, decider.apply(score), is(true));
        });
    }

    @Test
    public void shouldIgnoreOtherScores() {
        otherScores.forEach(score -> {
            assertThat("Score: " + score, decider.apply(score), is(false));
        });
    }
}
