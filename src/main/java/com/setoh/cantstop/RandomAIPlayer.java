package com.setoh.cantstop;

import java.util.List;
import java.util.Random;

import com.setoh.cantstop.Logic.DiceCombination;

public class RandomAIPlayer {
    
    private Random random = new Random();

    public DiceCombination chooseCombination(List<DiceCombination> combinations ){
        return combinations.get(random.nextInt(combinations.size()));
    }

    public boolean shouldContinue(){
        return random.nextDouble() < 0.5;
    }
}
