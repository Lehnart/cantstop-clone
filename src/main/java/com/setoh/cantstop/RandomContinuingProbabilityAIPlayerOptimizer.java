package com.setoh.cantstop;

import java.util.List;

public class RandomContinuingProbabilityAIPlayerOptimizer {

    public Float optimize(int gameCountPerProbability, List<Float> probabilities){
        float optimalProbability = 0.f;
        float optimalTurnCount = 100.f;
        
        for(float probabilityToContinue : probabilities){
            RandomContinuingProbabilityAIPlayer ai = new RandomContinuingProbabilityAIPlayer(probabilityToContinue);
            Logic logic = new Logic(ai, null);
            int turnCounts = 0;
            for(int i=0; i<gameCountPerProbability; i++){
                turnCounts += logic.playGame(null).getTurn();
            }
            float average = turnCounts/(float)gameCountPerProbability;
            if(average < optimalTurnCount){
                optimalTurnCount = average;
                optimalProbability = probabilityToContinue;
            }
        }
        return optimalProbability;
    }
}
