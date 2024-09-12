package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.setoh.cantstop.Logic.DiceCombination;

public class RandomAIPlayerTest {

    @Test
    public void testRandomAIPlayerPlays() {
        RandomAIPlayer aiPlayer = new RandomAIPlayer();
        List<DiceCombination> combinations = List.of(new DiceCombination(1,2,3,4));
        DiceCombination combination = aiPlayer.chooseCombination(combinations);
        assertThat(combination.dice1()).isEqualTo(1);
        assertThat(combination.dice2()).isEqualTo(2);
        assertThat(combination.dice3()).isEqualTo(3);
        assertThat(combination.dice4()).isEqualTo(4);
    }
}
