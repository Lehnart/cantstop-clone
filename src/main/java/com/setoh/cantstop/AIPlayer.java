package com.setoh.cantstop;

import java.util.List;

public abstract class AIPlayer {

    public abstract List<Integer> chooseCombination(List<List<Integer>> columnsToProgress);

    public abstract boolean shouldContinue(State state);

}
