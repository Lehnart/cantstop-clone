package com.setoh.cantstop;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CSVWriterTest {

    @Rule
    public TemporaryFolder folder = TemporaryFolder.builder().assureDeletion().build();

    @Test
    public void testAddBoardState() {
        State state = new State();
        CSVWriter csvWriter = new CSVWriter();
        state.temporaryProgress(2);
        state.progress();
        state.temporaryProgress(3);
        state.temporaryProgress(3);
        state.temporaryProgress(4);
        csvWriter.addBoardState(state);
        assertThat(csvWriter.getCurrentLine()).isEqualTo("1,0,0,0,0,0,0,0,0,0,0,null,2,1,null,null,null,null,null,null,null,null,2,");
    }

    @Test
    public void testAddChosenColumns() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addChosenColumns(List.of());
        csvWriter.addChosenColumns(List.of(1));
        csvWriter.addChosenColumns(List.of(2,3));
        assertThat(csvWriter.getCurrentLine()).isEqualTo(",,1,,2,3,");
    }

    @Test
    public void testAddDiceResult() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addDiceResult(List.of(1,2,3,4));
        assertThat(csvWriter.getCurrentLine()).isEqualTo("1,2,3,4,");
    }

    @Test
    public void testAddPlayerChoice() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addPlayerChoice(true);
        csvWriter.addPlayerChoice(false);  
        assertThat(csvWriter.getCurrentLine()).isEqualTo("true,false,");
    }

    @Test
    public void testSaveTurn() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addPlayerChoice(true);
        csvWriter.saveTurn();
        assertThat(csvWriter.getCurrentLine()).isEmpty();
        assertThat(csvWriter.getLines()).hasSize(1);
        assertThat(csvWriter.getLines().get(0)).isEqualTo("true,");
    }

    @Test
    public void testWrite( ) {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addPlayerChoice(true);
        csvWriter.saveTurn();
        csvWriter.addPlayerChoice(false);
        csvWriter.saveTurn();
        csvWriter.write(folder.getRoot() +"/"+"output.log");
        File output = new File(folder.getRoot() +"/"+ "output.log");
        assertThat(output).exists().isNotEmpty().hasContent("true,\nfalse,\n");
    }
}
