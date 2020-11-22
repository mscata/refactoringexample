package com.example.badcode.dicegame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameOutcomeDeciderTest {
    private GameOutcomeDecider decider = new GameOutcomeDecider();
    private ScoreHandler winHandler = new ScoreHandler(Outcome.WIN, 7, 11);
    private ScoreHandler loseHandler = new ScoreHandler(Outcome.LOSE, 2, 3, 12);
    private List<Integer> scoreRange = IntStream.range(2, 13).boxed().collect(Collectors.toList()); // upper boundary excluded
    private List<Integer> otherScores = new ArrayList<>(scoreRange); // clone the score range, then modify it

    public GameOutcomeDeciderTest() {
        decider.addHandler(winHandler);
        decider.addHandler(loseHandler);
        otherScores.removeAll(winHandler.getMatchingScores());
        otherScores.removeAll(loseHandler.getMatchingScores());
    }

    @Test
    public void shouldDecideWin() {
        winHandler.getMatchingScores().forEach(score -> {
            assertThat("Score: " + score, decider.apply(score), is(Outcome.WIN));
        });
    }

    @Test
    public void shouldDecideLose() {
        loseHandler.getMatchingScores().forEach(score -> {
            assertThat("Score: " + score, decider.apply(score), is(Outcome.LOSE));
        });
    }

    @Test
    public void shouldDecideDraw() {
       otherScores.forEach(score -> {
            assertThat("Score: " + score, decider.apply(score), is(Outcome.DRAW));
        });
    }
}
