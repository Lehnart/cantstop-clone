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
    public void testMainWithGameCountAndOutputPath(){
        main(new String[]{"5", folder.getRoot().toString()});
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
    public void testMainWithGameCountOnly(){
        main(new String[]{"5"});
        assertThat(folder.getRoot()).exists().isEmptyDirectory();
    }


    @Test
    public void testMainWithoutArgs(){
        assertThatThrownBy( () -> main(new String[]{}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("At least 1 argument is expected: gameCount [outputPath]");
    }
}
