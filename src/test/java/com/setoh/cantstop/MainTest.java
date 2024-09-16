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
    public void testMain(){
        main(new String[]{folder.getRoot().toString(),"5"});
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
    public void testMainWithoutArgs(){
        assertThatThrownBy( () -> main(new String[]{}))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("2 arguments are expected: outputPath and gameCount");
    }
}
