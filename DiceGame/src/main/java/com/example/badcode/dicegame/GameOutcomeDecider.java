package com.example.badcode.dicegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class GameOutcomeDecider implements Function<Integer, Outcome> {
    private List<ScoreHandler> scoreHandlers = new ArrayList<>();

    public void addHandler(ScoreHandler scoreHandler) {
        scoreHandlers.add(scoreHandler);
    }

    public Outcome apply(Integer score) {
        Optional<ScoreHandler> matchingHandler = scoreHandlers.stream()
                .filter(handler -> handler.apply(score))
                .findFirst();
        return matchingHandler.isPresent() ? matchingHandler.get().getOutcome() : Outcome.DRAW;
    }
}
