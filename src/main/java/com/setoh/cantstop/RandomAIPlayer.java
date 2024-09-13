package com.setoh.cantstop;

import java.util.List;
import java.util.Random;

public class RandomAIPlayer {

    private Random random = new Random();
    private double continuingProbability;

    public RandomAIPlayer(double probability) {
        continuingProbability = probability;
    }

    public List<Integer> chooseCombination(List<List<Integer>> columnsToProgress) {
        if (columnsToProgress.isEmpty()) {
            return List.of();
        }
        return columnsToProgress.get(random.nextInt(columnsToProgress.size()));
    }

    public boolean shouldContinue() {
        return random.nextDouble() < continuingProbability;
    }
}
