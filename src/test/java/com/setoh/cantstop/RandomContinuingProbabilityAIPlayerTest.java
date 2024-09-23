package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;

public class RandomContinuingProbabilityAIPlayerTest {

    @Test
    public void testShouldContinue() {
        RandomContinuingProbabilityAIPlayer alwaysStoppingAi = new RandomContinuingProbabilityAIPlayer(1.001);
        assertThat(alwaysStoppingAi.shouldContinue(new State())).isTrue();
        RandomContinuingProbabilityAIPlayer alwaysContinuingAi = new RandomContinuingProbabilityAIPlayer(0.);
        assertThat(alwaysContinuingAi.shouldContinue(new State())).isFalse();
    }

    @Test
    public void testPlays() {
        RandomContinuingProbabilityAIPlayer aiPlayer = new RandomContinuingProbabilityAIPlayer(0.5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of(5)));
        assertThat(columns).hasSize(1).containsExactly(5);
    }

    @Test
    public void testPlaysWithEmptyValidCombinations() {
        RandomContinuingProbabilityAIPlayer aiPlayer = new RandomContinuingProbabilityAIPlayer(0.5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of()));
        assertThat(columns).isEmpty();
    }
}
