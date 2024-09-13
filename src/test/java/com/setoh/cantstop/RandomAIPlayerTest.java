package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.setoh.cantstop.Logic.DiceCombination;

public class RandomAIPlayerTest {

    @Test
    public void testShouldContinue() {
        RandomAIPlayer alwaysStoppingAi = new RandomAIPlayer(1.001);
        assertThat(alwaysStoppingAi.shouldContinue()).isTrue();
        RandomAIPlayer alwaysContinuingAi = new RandomAIPlayer(0.);
        assertThat(alwaysContinuingAi.shouldContinue()).isFalse();
    }

    @Test
    public void testPlays() {
        RandomAIPlayer aiPlayer = new RandomAIPlayer(0.5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of(5)));
        assertThat(columns).hasSize(1);
        assertThat(columns).containsExactly(5);
    }

    @Test
    public void testPlaysWithEmptyValidCombinations() {
        RandomAIPlayer aiPlayer = new RandomAIPlayer(0.5);
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of()));
        assertThat(columns).isEmpty();
    }
}
