package com.setoh.cantstop;

import java.util.Map;

public class Logic {
    
    private Logic(){}

    public static boolean isCombinationValid(int dicePair1, int dicePair2, State state){
        return canProgressOnColumn(dicePair1, state) || canProgressOnColumn(dicePair2, state);
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
}
