package com.example.badcode.dicegame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ScoreHandler implements Function<Integer, Boolean> {
    private final List<Integer> matchingScores;
    private final Outcome outcome;

    public ScoreHandler(Outcome outcome, Integer ... scores) {
        this.matchingScores = Arrays.asList(scores);
        this.outcome = outcome;
    }

    @Override
    public Boolean apply(Integer score) {
        return matchingScores.contains(score);
    }

    public List<Integer> getMatchingScores() {
        return Collections.unmodifiableList(matchingScores);
    }

    public Outcome getOutcome() {
        return outcome;
    }
}
