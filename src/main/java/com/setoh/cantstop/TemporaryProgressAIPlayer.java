package com.setoh.cantstop;

import java.util.List;
import java.util.Random;
import java.util.Map;

public class TemporaryProgressAIPlayer extends AIPlayer{

    private final List<Integer> columnMaxs;
    private Random random = new Random();

    public TemporaryProgressAIPlayer(List<Integer> columnMaxs) {
        this.columnMaxs = columnMaxs;
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
        for(int column : state.columns()){
            int progress = state.getTemporaryHeight(column) - state.getPlayerHeight(column);
            if( columnMaxs.get(column-2) <= progress){
                return false;
            }
        }
        return true;
    }
}
