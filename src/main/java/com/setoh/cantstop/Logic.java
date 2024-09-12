package com.setoh.cantstop;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Logic {
    
    private static Random random = new Random();

    private RandomAIPlayer aiPlayer;

    public Logic(){
        aiPlayer = new RandomAIPlayer();
    }

    public DiceCombination play(State state){
        List<Integer> dices = rollDices();
        List<DiceCombination> validCombinations = validCombinations(dices, state);
        return aiPlayer.chooseCombination(validCombinations);
    }

    public static List<DiceCombination> validCombinations(List<Integer> dices, State state){
        DiceCombination c1 = new DiceCombination(dices.get(0), dices.get(1), dices.get(2), dices.get(3));
        DiceCombination c2 = new DiceCombination(dices.get(0), dices.get(2), dices.get(1), dices.get(3));
        DiceCombination c3 = new DiceCombination(dices.get(0), dices.get(3), dices.get(1), dices.get(2));
        return List.of(c1, c2, c3).stream().filter(c -> isCombinationValid(c, state)).toList();
    }

    public static List<Integer> rollDices(){
        return List.of(rollDice(), rollDice(), rollDice(), rollDice());
    }

    static int rollDice(){
        return random.nextInt(6)+1;
    }

    public static boolean isCombinationValid(DiceCombination combination, State state){
        return canProgressOnColumn(combination.getFirstSum(), state) || canProgressOnColumn(combination.getSecondSum(), state);
    }

    static boolean canProgressOnColumn(int column, State state){
        if (state.isColumnClaimed(column)){
            return false;
        }
        
        Map<Integer, Integer> temporaryHeights = state.getTemporaryHeights();
        if(temporaryHeights.containsKey(column)){
            return temporaryHeights.get(column) <= State.COLUMN_HEIGHTS.get(column);
        }

        else if(temporaryHeights.size() == 3){
            return false;
        }
        return true;
    }

    public static record DiceCombination(int dice1, int dice2, int dice3, int dice4) {
        public int getFirstSum(){
            return dice1 + dice2;
        }
        public int getSecondSum(){
            return dice3 + dice4;
        }
    }
}
