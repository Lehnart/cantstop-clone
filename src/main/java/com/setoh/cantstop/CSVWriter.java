package com.setoh.cantstop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    private StringBuilder currentLine = new StringBuilder();
    private List<String> lines = new ArrayList<>();

    public void addBoardState(State state) {
        for (int column : state.columns()) {
            currentLine.append(state.getPlayerHeight(column)).append(",");
        }
        for (int column : state.columns()) {
            currentLine.append(state.getTemporaryHeights().get(column)).append(",");
        }
        currentLine.append(state.getTurn()).append(",");
    }

    public void addDiceResult(List<Integer> dices) {
        currentLine.append(dices.get(0)).append(",");
        currentLine.append(dices.get(1)).append(",");
        currentLine.append(dices.get(2)).append(",");
        currentLine.append(dices.get(3)).append(",");
    }

    public void addChosenColumns(List<Integer> columns) {
        if (columns.isEmpty()) {
            currentLine.append(",,");
        } else if (columns.size() == 1) {
            currentLine.append(columns.get(0)).append(",,");
        } else if (columns.size() == 2) {
            currentLine.append(columns.get(0)).append(",").append(columns.get(1)).append(",");
        }
    }

    public void addPlayerChoice(boolean shouldContinue) {
        currentLine.append(shouldContinue).append(",");
    }

    public void saveTurn() {
        lines.add(currentLine.toString());
        currentLine = new StringBuilder();
    }

    public void write(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    String getCurrentLine(){
        return currentLine.toString();
    }

    List<String> getLines(){
        return lines;
    }
}
