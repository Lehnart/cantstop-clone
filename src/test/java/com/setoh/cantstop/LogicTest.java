package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LogicTest {
    
    @Test
    public void testCanProgressOnColumnWhenColumnClaimed() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.progress();

        assertThat(Logic.canProgressOnColumn(2, state)).isFalse();
    }
    
    @Test
    public void testCanProgressOnColumnWhenColumnIsAlreadyProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        assertThat(Logic.canProgressOnColumn(2, state)).isFalse();
        assertThat(Logic.canProgressOnColumn(5, state)).isTrue();        
    }

    @Test
    public void testCanProgressOnColumnWhenColumnIsNotAlreadyProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        assertThat(Logic.canProgressOnColumn(6, state)).isTrue();        
    }

    @Test
    public void testCanProgressOnColumnWhenColumnIsNotAlreadyProgressingAnd3ColumnsAreProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.temporaryProgress(7);
        assertThat(Logic.canProgressOnColumn(6, state)).isFalse();        
    }

    @Test
    public void testIsCombinationValid() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.temporaryProgress(7);
        assertThat(Logic.isCombinationValid(3, 4, state)).isFalse();
        assertThat(Logic.isCombinationValid(3, 5, state)).isTrue();
        assertThat(Logic.isCombinationValid(2, 4, state)).isTrue();
        assertThat(Logic.isCombinationValid(2, 5, state)).isTrue();
                
    }
}
