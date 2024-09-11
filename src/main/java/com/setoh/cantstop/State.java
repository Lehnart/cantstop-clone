package com.setoh.cantstop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class State {

    public static final Map<Integer, Integer> COLUMN_HEIGHTS = Map.ofEntries(
            Map.entry(2, 3), Map.entry(3, 5), Map.entry(4, 7), Map.entry(5, 9), Map.entry(6, 11), Map.entry(7, 13),
            Map.entry(8, 11), Map.entry(9, 9), Map.entry(10, 7), Map.entry(11, 5), Map.entry(12, 3)
    );

    private final Map<Integer, Integer> playerHeightsPerColumn = Map.ofEntries(
            Map.entry(2, 0), Map.entry(3, 0), Map.entry(4, 0), Map.entry(5, 0), Map.entry(6, 0), Map.entry(7, 0),
            Map.entry(8, 0), Map.entry(9, 0), Map.entry(10, 0), Map.entry(11, 0), Map.entry(12, 0)
    );

    private final Map<Integer, Integer> temporaryHeights = new HashMap<>();

    private int turn = 1;

    public Set<Integer> columns() {
        return COLUMN_HEIGHTS.keySet();
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
        return Map.copyOf(temporaryHeights);
    }
}
