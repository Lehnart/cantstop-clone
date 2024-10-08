package com.setoh.cantstop;

import java.util.List;
import java.util.Random;

public class RandomContinuingProbabilityAIPlayer extends AIPlayer{

    private Random random = new Random();
    private final double continuingProbability;

    public RandomContinuingProbabilityAIPlayer(double probability) {
        continuingProbability = probability;
    }

    @Override
    public List<Integer> chooseCombination(List<List<Integer>> columnsToProgress) {
        if (columnsToProgress.isEmpty()) {
            return List.of();
        }
        return columnsToProgress.get(random.nextInt(columnsToProgress.size()));
    }

    @Override
    public boolean shouldContinue(State state) {
        return random.nextDouble() < continuingProbability;
    }
}
