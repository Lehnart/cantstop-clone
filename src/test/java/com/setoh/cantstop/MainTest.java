package com.setoh.cantstop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.util.Arrays;

import static com.setoh.cantstop.Main.main;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

public class MainTest {
    @Rule
    public TemporaryFolder folder = TemporaryFolder.builder().assureDeletion().build();

    @Test
    public void testMainWithoutArgs(){
        assertThatThrownBy( () -> main(new String[]{}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("First argument should be a command --run, --optimize");
    }

    @Test
    public void testMainWithUnknownCommand(){
        assertThatThrownBy( () -> main(new String[]{"--unknownCommand"}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Command --unknownCommand is unknown.");
    }

    @Test
    public void testMainRunWithoutArsg(){
        assertThatThrownBy( () -> main(new String[]{"--run"}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least 1 argument is expected: --run gameCount [outputPath]");
    }

    @Test
    public void testMainRunWithGameCountOnly(){
        main(new String[]{"--run","5"});
        assertThat(folder.getRoot()).exists().isEmptyDirectory();
    }

    @Test
    public void testMainRunWithGameCountAndOutputPath(){
        main(new String[]{"--run", "5", folder.getRoot().toString()});
        assertThat(folder.getRoot()).exists().isNotEmptyDirectory();
        assertThat(folder.getRoot().listFiles()).hasSize(5);
        assertThat(Arrays.stream(folder.getRoot().listFiles()).map(f -> f.getName()).toList()).containsExactlyInAnyOrder(
            "output_1.log", "output_2.log", "output_3.log", "output_4.log", "output_5.log"
            );
        for( File file : folder.getRoot().listFiles()){
            assertThat(file).isNotEmpty();
        }
    }

    @Test
    public void testMainOptimizeWithoutArgs(){
        assertThatThrownBy( () -> main(new String[]{"--optimize"}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least 5 arguments are expected: --optimize aiName gameCount start stop step");
    }

    @Test
    public void testMainRandomOptimize(){
        float optimalProbability = Main.optimize(new String[]{"--optimize", "RandomContinuingProbabilityAIPlayer", "100", "0.1", "1.","0.6"});
        assertThat(optimalProbability).isBetween(0.65f, 0.75f);
    }

    @Test
    public void testMainFixOptimize(){
        float optimalProbability = Main.optimize(new String[]{"--optimize", "FixContinueCountAIPlayer", "100", "1", "3","2"});
        assertThat(optimalProbability).isBetween(2.5f, 3.5f);
    }
}
