package com.setoh.cantstop;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;

public class StateTest {

    @Test
    public void testStateConstructor() {
        State state = new State();
        assertThat(State.COLUMN_HEIGHTS).isEqualTo(
            Map.ofEntries(
                Map.entry(2, 3), Map.entry(3, 5), Map.entry(4, 7), Map.entry(5, 9), Map.entry(6, 11), Map.entry(7, 13),
                Map.entry(8, 11), Map.entry(9, 9), Map.entry(10, 7), Map.entry(11, 5), Map.entry(12, 3)
            )
        );
        assertThat(state.columns()).isEqualTo(Set.of(2,3,4,5,6,7,8,9,10,11,12));
        assertThat(state.getTemporaryHeights()).isEmpty();   
        for(int column : state.columns()){
            assertThat(state.getPlayerHeight(column)).isZero();
        }
    }
}
