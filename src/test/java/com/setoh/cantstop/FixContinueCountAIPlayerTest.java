package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;

public class FixContinueCountAIPlayerTest {

    @Test
    public void testShouldContinue() {
        FixContinueCountAIPlayer alwaysStoppingAi = new FixContinueCountAIPlayer(0);
        assertThat(alwaysStoppingAi.shouldContinue(new State())).isFalse();
        FixContinueCountAIPlayer alwaysContinuingAi = new FixContinueCountAIPlayer(1);
        assertThat(alwaysContinuingAi.shouldContinue(new State())).isTrue();
    }

    @Test
    public void testPlays() {
        FixContinueCountAIPlayer aiPlayer = new FixContinueCountAIPlayer(5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of(5)));
        assertThat(columns).hasSize(1).containsExactly(5);
    }

    @Test
    public void testPlays2Turns() {
        State state = new State();
        FixContinueCountAIPlayer aiPlayer = new FixContinueCountAIPlayer(1);
        Logic logic = new Logic(aiPlayer, null);
        logic.playTurn(state);
        assertThat(aiPlayer.lastTurn).isEqualTo(1);     
        logic.playTurn(state);
        assertThat(aiPlayer.lastTurn).isEqualTo(2);
    }

    @Test
    public void testPlaysWithEmptyValidCombinations() {
        FixContinueCountAIPlayer aiPlayer = new FixContinueCountAIPlayer(5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of());
        assertThat(columns).isEmpty();
    }
}
