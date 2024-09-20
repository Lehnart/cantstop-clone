package com.setoh.cantstop;

public class Main {
    public static void main(String[] args){
        if (args.length < 1){
            throw new IllegalArgumentException("At least 1 argument is expected: gameCount [outputPath]");
        }
        int gameCount;
        String outputPath = null;
        gameCount = Integer.parseInt(args[0]);
        if(args.length == 2){
           outputPath = args[1];        
        }
        for(int i = 1; i <=gameCount; i++){
            Logic logic = new Logic(new RandomAIPlayer(0.8), outputPath == null ? null : new CSVWriter());
            logic.playGame(outputPath + "/output_"+i+".log");
        }
    }
}
