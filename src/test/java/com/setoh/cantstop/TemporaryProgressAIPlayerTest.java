package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class TemporaryProgressAIPlayerTest {

    @Test
    public void testShouldContinue() {
        List<Integer> maxHeightToAlwaysStop  = new ArrayList<Integer>();
        for( int i = 0 ; i < 11 ; i++ ){
            maxHeightToAlwaysStop.add(0);
        }
        TemporaryProgressAIPlayer alwaysStoppingAi = new TemporaryProgressAIPlayer(maxHeightToAlwaysStop);
        assertThat(alwaysStoppingAi.shouldContinue(new State())).isFalse();

        List<Integer> maxHeightToAlwaysContinue  = new ArrayList<Integer>();
        for( int i = 0 ; i < 11 ; i++ ){
            maxHeightToAlwaysContinue.add(13);
        }
        TemporaryProgressAIPlayer alwaysContinuingAi = new TemporaryProgressAIPlayer(maxHeightToAlwaysContinue);
        assertThat(alwaysContinuingAi.shouldContinue(new State())).isTrue();
    }

    @Test
    public void testPlays() {
        TemporaryProgressAIPlayer aiPlayer = new TemporaryProgressAIPlayer(List.of());
        List<Integer> columns = aiPlayer.chooseCombination(List.of(List.of(5)));
        assertThat(columns).hasSize(1).containsExactly(5);
    }

    @Test
    public void testPlaysWithEmptyValidCombinations() {
        TemporaryProgressAIPlayer aiPlayer = new TemporaryProgressAIPlayer(List.of());
        List<Integer> columns = aiPlayer.chooseCombination(List.of());
        assertThat(columns).isEmpty();
    }
}
