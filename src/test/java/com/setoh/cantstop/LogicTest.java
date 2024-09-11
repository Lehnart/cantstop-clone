package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;

import com.setoh.cantstop.Logic.DiceCombination;

public class LogicTest {
    
    @Test 
    public void testValidCombinationWhenAllCombinationsAreValid(){
        State state = new State();
        List<DiceCombination> combinations = Logic.validCombinations(List.of(1,2,3,4), state);
        assertThat(combinations).hasSize(3);
        assertThat(combinations.get(0).dice1()).isEqualTo(1);
        assertThat(combinations.get(0).dice2()).isEqualTo(2);
        assertThat(combinations.get(0).dice3()).isEqualTo(3);
        assertThat(combinations.get(0).dice4()).isEqualTo(4);
        assertThat(combinations.get(1).dice1()).isEqualTo(1);
        assertThat(combinations.get(1).dice2()).isEqualTo(3);
        assertThat(combinations.get(1).dice3()).isEqualTo(2);
        assertThat(combinations.get(1).dice4()).isEqualTo(4);
        assertThat(combinations.get(2).dice1()).isEqualTo(1);
        assertThat(combinations.get(2).dice2()).isEqualTo(4);
        assertThat(combinations.get(2).dice3()).isEqualTo(2);
        assertThat(combinations.get(2).dice4()).isEqualTo(3);
    }

    @Test 
    public void testValidCombinationWhenNoCombinationIsValid(){
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.progress();
        List<DiceCombination> combinations = Logic.validCombinations(List.of(1,1,1,1), state);
        assertThat(combinations).isEmpty();
    }

    @Test 
    public void testRollDice(){
        assertThat(Logic.rollDice()).isPositive().isLessThanOrEqualTo(6);
    }

    @Test 
    public void testRollDices(){
        assertThat(Logic.rollDices()).hasSize(4);
    }

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
        assertThat(Logic.isCombinationValid(new DiceCombination(1,2,2,2), state)).isFalse();
        assertThat(Logic.isCombinationValid(new DiceCombination(1,2,2,3), state)).isTrue();
        assertThat(Logic.isCombinationValid(new DiceCombination(1,1,2,2), state)).isTrue();
        assertThat(Logic.isCombinationValid(new DiceCombination(1,1,2,3), state)).isTrue();
    }

    @Test
    public void testDiceCombination() {
        DiceCombination diceCombination = new DiceCombination(1, 2, 3, 4);
        assertThat(diceCombination.dice1()).isEqualTo(1);
        assertThat(diceCombination.dice2()).isEqualTo(2);
        assertThat(diceCombination.dice3()).isEqualTo(3);
        assertThat(diceCombination.dice4()).isEqualTo(4);
        assertThat(diceCombination.getFirstSum()).isEqualTo(3);
        assertThat(diceCombination.getSecondSum()).isEqualTo(7);
    }

}
