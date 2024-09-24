package com.setoh.cantstop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

    public static final Map<Integer, Integer> COLUMN_HEIGHTS = Map.ofEntries(
            Map.entry(2, 3), Map.entry(3, 5), Map.entry(4, 7), Map.entry(5, 9), Map.entry(6, 11), Map.entry(7, 13),
            Map.entry(8, 11), Map.entry(9, 9), Map.entry(10, 7), Map.entry(11, 5), Map.entry(12, 3));

    private final Map<Integer, Integer> playerHeightsPerColumn = new HashMap<>(
            Map.ofEntries(
                    Map.entry(2, 0), Map.entry(3, 0), Map.entry(4, 0), Map.entry(5, 0), Map.entry(6, 0),
                    Map.entry(7, 0),
                    Map.entry(8, 0), Map.entry(9, 0), Map.entry(10, 0), Map.entry(11, 0), Map.entry(12, 0))
                    );

    private final Map<Integer, Integer> temporaryHeights = new HashMap<>();

    private int turn = 1;

    public List<Integer> columns() {
        return List.of(2,3,4,5,6,7,8,9,10,11,12);
    }

    public int getTurn() {
        return turn;
    }

    public int getPlayerHeight(int column) {
        if (!columns().contains(column)) {
            throw new IllegalArgumentException("Column " + column + " is not a valid column key.");
        }
        return playerHeightsPerColumn.get(column);
    }

    public Map<Integer, Integer> getTemporaryHeights() {
        return new HashMap<>(temporaryHeights);
    }

    public int getTemporaryHeight(int column) {
        if(temporaryHeights.containsKey(column)){
            return temporaryHeights.get(column);
        }
        return 0;
    }


    public boolean isColumnClaimed(int column) {
        return getPlayerHeight(column) >= COLUMN_HEIGHTS.get(column);
    }

    public void temporaryProgress(int column) {
        temporaryHeights.putIfAbsent(column, playerHeightsPerColumn.get(column));
        temporaryHeights.put(column, temporaryHeights.get(column) + 1);
    }

    public void progress() {
        for (Map.Entry<Integer, Integer> columnAndHeight : temporaryHeights.entrySet()) {
            playerHeightsPerColumn.put(columnAndHeight.getKey(), columnAndHeight.getValue());
        }
        turn += 1;
        temporaryHeights.clear();
    }

    public void failToProgress() {
        turn += 1;
        temporaryHeights.clear();
    }

    public int getColumnClaimedCount() {
        int count = 0;
        for(int column : columns()){
            if(isColumnClaimed(column)){
                count += 1;
            }
        }
        return count;
    }
}
