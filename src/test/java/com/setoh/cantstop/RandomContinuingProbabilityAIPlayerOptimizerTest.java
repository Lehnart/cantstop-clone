package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;


public class RandomContinuingProbabilityAIPlayerOptimizerTest {
    @Test
    public void testOptimizer() {
        RandomContinuingProbabilityAIPlayerOptimizer optimizer = new RandomContinuingProbabilityAIPlayerOptimizer();
        float optimalProbability = optimizer.optimize(100, List.of(0.1f, 0.7f));
        assertThat(optimalProbability).isBetween(0.65f, 0.75f);
    }
}
