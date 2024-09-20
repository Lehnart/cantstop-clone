package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.setoh.cantstop.Logic.DiceCombination;

public class LogicTest {

    @Rule
    public TemporaryFolder folder = TemporaryFolder.builder().assureDeletion().build();

    @Test
    public void testColumnToProgressWhenAllCombinationsAreValid() {
        State state = new State();
        List<List<Integer>> combinations = Logic.getColumnsToProgress(List.of(1, 2, 3, 4), state);
        assertThat(combinations).hasSize(3);
        assertThat(combinations.get(0)).hasSize(2);
        assertThat(combinations.get(0)).containsExactly(3, 7);
        assertThat(combinations.get(1)).hasSize(2);
        assertThat(combinations.get(1)).containsExactly(4, 6);
        assertThat(combinations.get(2)).hasSize(2);
        assertThat(combinations.get(2)).containsExactly(5, 5);
    }

    @Test
    public void testColumnToProgressWhenNoCombinationIsValid() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.progress();
        List<List<Integer>> combinations = Logic.getColumnsToProgress(List.of(1, 1, 1, 1), state);
        assertThat(combinations).isEmpty();
    }

    @Test
    public void testColumnToProgressWhenOnlyOnePairIsValid() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(12);
        state.temporaryProgress(12);
        state.temporaryProgress(12);
        state.temporaryProgress(12);
        List<List<Integer>> combinations = Logic.getColumnsToProgress(List.of(1, 2, 3, 4), state);
        assertThat(combinations).hasSize(5);
        assertThat(combinations.get(0)).containsExactly(3);
        assertThat(combinations.get(1)).containsExactly(7);
        assertThat(combinations.get(2)).containsExactly(4);
        assertThat(combinations.get(3)).containsExactly(6);
        assertThat(combinations.get(4)).containsExactly(5, 5);
    }

    @Test
    public void testRollDice() {
        assertThat(Logic.rollDice()).isPositive().isLessThanOrEqualTo(6);
    }

    @Test
    public void testRollDices() {
        assertThat(Logic.rollDices()).hasSize(4);
    }

    @Test
    public void testCanProgressOnColumnsWhenColumnClaimed() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.progress();

        assertThat(Logic.canProgressOnColumns(List.of(2), state)).isFalse();
    }

    @Test
    public void testCanProgressOnColumnsWhenColumnIsAlreadyProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        assertThat(Logic.canProgressOnColumns(List.of(2), state)).isFalse();
        assertThat(Logic.canProgressOnColumns(List.of(5), state)).isTrue();
    }

    @Test
    public void testCanProgressOnColumnWhenColumnIsNotAlreadyProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        assertThat(Logic.canProgressOnColumns(List.of(6), state)).isTrue();
    }

    @Test
    public void testCanProgressOnColumnsWhenColumnIsNotAlreadyProgressingAnd3ColumnsAreProgressing() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.temporaryProgress(7);
        assertThat(Logic.canProgressOnColumns(List.of(6), state)).isFalse();
    }

    @Test
    public void testGetColumnsToProgress() {
        State state = new State();
        state.temporaryProgress(2);
        state.temporaryProgress(5);
        state.temporaryProgress(7);
        assertThat(Logic.getColumnsToProgress(new DiceCombination(1, 2, 2, 2), state)).isEmpty();
        assertThat(Logic.getColumnsToProgress(new DiceCombination(1, 2, 2, 3), state)).isNotEmpty();
        assertThat(Logic.getColumnsToProgress(new DiceCombination(1, 1, 2, 2), state)).isNotEmpty();
        assertThat(Logic.getColumnsToProgress(new DiceCombination(1, 1, 2, 3), state)).isNotEmpty();
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

    @Test
    public void testPlayTurnAlwaysFail() {
        State state = new State();
        Logic logic = new Logic(new RandomAIPlayer(1.), null);
        logic.playTurn(state);
        for (int column : state.columns()) {
            assertThat(state.getPlayerHeight(column)).isZero();
        }
    }

    @Test
    public void testPlayTurnAlwaysStop() {
        State state = new State();
        Logic logic = new Logic(new RandomAIPlayer(0.), null);
        logic.playTurn(state);
        assertThat(state.columns().stream().map(c -> state.getPlayerHeight(c)).anyMatch(h -> h > 0)).isTrue();
    }

    @Test
    public void testPlayGameWithoutOutput() {
        Logic logic = new Logic(new RandomAIPlayer(0.8), null);
        State state = logic.playGame("output.log");
        assertThat(state.getColumnClaimedCount()).isGreaterThanOrEqualTo(3);
        assertThat(folder.getRoot()).isEmptyDirectory();
    }

    @Test
    public void testPlayGameWithOutput() {
        Logic logic = new Logic(new RandomAIPlayer(0.8), new CSVWriter());
        State state = logic.playGame(folder.getRoot().toString() + "/output.log");
        assertThat(state.getColumnClaimedCount()).isGreaterThanOrEqualTo(3);
        assertThat(folder.getRoot()).isNotEmptyDirectory();
        File output = new File(folder.getRoot() +"/"+ "output.log");
        assertThat(output).exists().isNotEmpty();
    }

}
