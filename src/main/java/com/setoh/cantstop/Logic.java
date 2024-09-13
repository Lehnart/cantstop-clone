package com.setoh.cantstop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Logic {

    private static Random random = new Random();

    private RandomAIPlayer aiPlayer;

    public Logic(RandomAIPlayer aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public void play(State state) {
        List<Integer> chosenColumns;
        do {
            List<Integer> dices = rollDices();
            List<List<Integer>> columnsToProgress = getColumnsToProgress(dices, state);
            chosenColumns = aiPlayer.chooseCombination(columnsToProgress);
            if (!chosenColumns.isEmpty()) {
                for (int column : chosenColumns) {
                    state.temporaryProgress(column);
                }
            }
        } while (!chosenColumns.isEmpty() && aiPlayer.shouldContinue());

        if (!chosenColumns.isEmpty()) {
            state.progress();
        } else {
            state.failToProgress();
        }
    }

    public static List<List<Integer>> getColumnsToProgress(List<Integer> dices, State state) {
        DiceCombination c1 = new DiceCombination(dices.get(0), dices.get(1), dices.get(2), dices.get(3));
        DiceCombination c2 = new DiceCombination(dices.get(0), dices.get(2), dices.get(1), dices.get(3));
        DiceCombination c3 = new DiceCombination(dices.get(0), dices.get(3), dices.get(1), dices.get(2));
        return List.of(c1, c2, c3).stream().flatMap(c -> getColumnsToProgress(c, state).stream()).toList();
    }

    public static List<Integer> rollDices() {
        return List.of(rollDice(), rollDice(), rollDice(), rollDice());
    }

    static int rollDice() {
        return random.nextInt(6) + 1;
    }

    static List<List<Integer>> getColumnsToProgress(DiceCombination combination, State state) {
        List<List<Integer>> columnList = new ArrayList<>();
        if (canProgressOnColumns(List.of(combination.getFirstSum(),combination.getSecondSum()), state)) {
            columnList.add(List.of(combination.getFirstSum(), combination.getSecondSum()));
        } else {
            if (canProgressOnColumns(List.of(combination.getFirstSum()), state)) {
                columnList.add(List.of(combination.getFirstSum()));
            }
            if (canProgressOnColumns(List.of(combination.getSecondSum()), state)) {
                columnList.add(List.of(combination.getSecondSum()));
            }
        }
        return columnList;
    }

    static boolean canProgressOnColumns(List<Integer> columns, State state) {
        for (int column : columns) {
            if (state.isColumnClaimed(column)) {
                return false;
            }
        }

        Map<Integer, Integer> temporaryHeights = state.getTemporaryHeights();
        for (int column : columns) {
            if (temporaryHeights.containsKey(column)) {
                if (temporaryHeights.get(column) > State.COLUMN_HEIGHTS.get(column)) {
                    return false;
                }
                temporaryHeights.put(column, temporaryHeights.get(column) + 1);
            } else {
                if (temporaryHeights.size() == 3) {
                    return false;
                }
                temporaryHeights.put(column, state.getPlayerHeight(column));
            }
        }
        return true;
    }

    public static record DiceCombination(int dice1, int dice2, int dice3, int dice4) {
        public int getFirstSum() {
            return dice1 + dice2;
        }

        public int getSecondSum() {
            return dice3 + dice4;
        }
    }
}
