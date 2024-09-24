package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;


public class FixContinueCountAIPlayerOptimizerTest {
    @Test
    public void testOptimizer() {
        FixContinueCountAIPlayerOptimizer optimizer = new FixContinueCountAIPlayerOptimizer();
        float optimum = optimizer.optimize(1000, List.of(1,3));
        assertThat(optimum).isBetween(2.5f, 3.5f);
    }
}
