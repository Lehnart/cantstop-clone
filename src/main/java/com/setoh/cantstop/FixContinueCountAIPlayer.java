package com.setoh.cantstop;

import java.util.List;
import java.util.Random;

public class FixContinueCountAIPlayer extends AIPlayer{

    private final int continuingTurns;
    int lastTurn = -1;
    int turnCount = 0; 

    private Random random = new Random();

    public FixContinueCountAIPlayer(int continuingTurns) {
        this.continuingTurns = continuingTurns;
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
        if(state.getTurn()==lastTurn){
            turnCount++;
        }
        else{
            turnCount=1;
            lastTurn = state.getTurn();
        }
        return turnCount <= continuingTurns;
    }
}
