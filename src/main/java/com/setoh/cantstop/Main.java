package com.setoh.cantstop;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        if (args.length < 1){
            throw new IllegalArgumentException("First argument should be a command --run, --optimize");
        }

        String command = args[0];
        if(command.equals("--run")){
            run(args);
        }
        else if(command.equals("--optimize")){
            optimize(args);
        }
        else{
            throw new IllegalArgumentException("Command " + args[0] + " is unknown.");
        }
    }

    static float optimize(String[] args){
        if (args.length < 5){
            throw new IllegalArgumentException("At least 4 arguments are expected: --optimize gameCount start stop step");
        }
        int gameCount = Integer.parseInt(args[1]);
        float start = Float.parseFloat(args[2]);
        float stop = Float.parseFloat(args[3]);
        float step = Float.parseFloat(args[4]);
        List<Float> probabilities = new ArrayList<>();
        for(float proba = start; proba <= stop; proba += step){
            probabilities.add(proba);
        }
        float optimalProbability = new RandomContinuingProbabilityAIPlayerOptimizer().optimize(gameCount, probabilities);
        System.out.println("Optimal probability is " + optimalProbability);
        return optimalProbability;
    }

    private static void run(String[] args){
        if (args.length < 2){
            throw new IllegalArgumentException("At least 1 argument is expected: --run gameCount [outputPath]");
        }

        int gameCount;
        String outputPath = null;
        gameCount = Integer.parseInt(args[1]);
        if(args.length == 3){
           outputPath = args[2];        
        }
        for(int i = 1; i <=gameCount; i++){
            Logic logic = new Logic(new RandomContinuingProbabilityAIPlayer(0.8), outputPath == null ? null : new CSVWriter());
            logic.playGame(outputPath + "/output_"+i+".log");
        }

    }
}
