package com.setoh.cantstop;

import java.util.List;

public class FixContinueCountAIPlayerOptimizer {

    public int optimize(int gameCountPerTurn, List<Integer> maxTurns){
        int optimalTurnCount = 0;
        float optimalGameOverTurnCount = 100.f;
        
        for(int maxTurn : maxTurns){
            FixContinueCountAIPlayer ai = new FixContinueCountAIPlayer(maxTurn);
            Logic logic = new Logic(ai, null);
            int turnCounts = 0;
            for(int i=0; i<gameCountPerTurn; i++){
                turnCounts += logic.playGame(null).getTurn();
            }
            float average = turnCounts/(float)gameCountPerTurn;   
            if(average < optimalGameOverTurnCount){
                optimalGameOverTurnCount = average;
                optimalTurnCount = maxTurn;
            }
        }
        return optimalTurnCount;
    }
}
