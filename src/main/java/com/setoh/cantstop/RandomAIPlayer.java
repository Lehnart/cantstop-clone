package com.setoh.cantstop;

import java.util.List;
import java.util.Random;

import com.setoh.cantstop.Logic.DiceCombination;

public class RandomAIPlayer {
    
    private Random random = new Random();
    private double continuingProbability;

    public RandomAIPlayer(double probability){
        continuingProbability = probability;
    }

    public DiceCombination chooseCombination(List<DiceCombination> combinations ){
        if(combinations.isEmpty()){
            return null;
        }
        return combinations.get(random.nextInt(combinations.size()));
    }

    public boolean shouldContinue(){
        return random.nextDouble() > continuingProbability;
    }
}
