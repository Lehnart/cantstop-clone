package com.setoh.cantstop;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import java.util.Set;

public class StateTest {

    @Test
    public void testStateConstructor() {
        State state = new State();
        assertThat(Map.ofEntries(
                Map.entry(2, 3), Map.entry(3, 5), Map.entry(4, 7), Map.entry(5, 9), Map.entry(6, 11), Map.entry(7, 13),
                Map.entry(8, 11), Map.entry(9, 9), Map.entry(10, 7), Map.entry(11, 5), Map.entry(12, 3))).isEqualTo(
                        State.COLUMN_HEIGHTS);
        assertThat(state.getTurn()).isOne();
        assertThat(state.columns()).isEqualTo(Set.of(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertThat(state.getTemporaryHeights()).isEmpty();
        for (int column : state.columns()) {
            assertThat(state.getPlayerHeight(column)).isZero();
        }
        assertThatThrownBy(() -> state.getPlayerHeight(1)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Column 1 is not a valid column key.");
    }

    @Test
    public void testTemporaryProgress() {
        State state = new State();
        assertThat(state.getTemporaryHeights()).isEmpty();
        state.temporaryProgress(2);
        assertThat(state.getTemporaryHeights()).hasSize(1);
        assertThat(state.getTemporaryHeights()).containsOnly(Map.entry(2, 1));
        state.temporaryProgress(2);
        assertThat(state.getTemporaryHeights()).hasSize(1);
        assertThat(state.getTemporaryHeights()).containsOnly(Map.entry(2, 2));
        state.temporaryProgress(5);
        assertThat(state.getTemporaryHeights()).hasSize(2);
        assertThat(state.getTemporaryHeights()).containsOnly(Map.entry(2, 2), Map.entry(5, 1));
    }

    @Test
    public void testProgress() {
        State state = new State();
        assertThat(state.getTemporaryHeights()).isEmpty();
        state.progress();
        assertThat(state.getTemporaryHeights()).isEmpty();
        for (int column : state.columns()) {
            assertThat(state.getPlayerHeight(column)).isZero();
        }
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.progress();
        assertThat(state.getTemporaryHeights()).isEmpty();
        for (int column : state.columns()) {
            if (column == 2) {
                assertThat(state.getPlayerHeight(column)).isEqualTo(2);
            } else if (column == 5) {
                assertThat(state.getPlayerHeight(column)).isEqualTo(1);
            } else {
                assertThat(state.getPlayerHeight(column)).isZero();
            }
        }
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.progress();
        assertThat(state.getTemporaryHeights()).isEmpty();
        for (int column : state.columns()) {
            if (column == 2) {
                assertThat(state.getPlayerHeight(column)).isEqualTo(4);
            } else if (column == 5) {
                assertThat(state.getPlayerHeight(column)).isEqualTo(2);
            } else {
                assertThat(state.getPlayerHeight(column)).isZero();
            }
        }
    }

    @Test
    public void testIsColumnClaimed() {
        State state = new State();
        for (int column : state.columns()) {
            assertThat(state.isColumnClaimed(column)).isFalse();
        }
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.progress();
        for (int column : state.columns()) {
            assertThat(state.isColumnClaimed(column)).isFalse();
        }
        state.temporaryProgress(2);
        state.progress();
        for (int column : state.columns()) {
            if (column == 2) {
                assertThat(state.isColumnClaimed(column)).isTrue();
            } else {
                assertThat(state.isColumnClaimed(column)).isFalse();
            }
        }
    }
}
