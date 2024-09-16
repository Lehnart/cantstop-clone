package com.setoh.cantstop;

public class Main {
    public static void main(String[] args){
        if (args.length < 2){
            throw new IllegalArgumentException("2 arguments are expected: outputPath and gameCount");
        }
        String outputPath = args[0];
        int gameCount;
        gameCount = Integer.parseInt(args[1]);
        for(int i = 1; i <=gameCount; i++){
            Logic logic = new Logic(new RandomAIPlayer(0.8));
            logic.playGame(outputPath + "/output_"+i+".log");
        }
    }
}
