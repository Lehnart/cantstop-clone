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
        if (args.length < 6){
            throw new IllegalArgumentException("At least 5 arguments are expected: --optimize aiName gameCount start stop step");
        }
        String aiName = args[1];
        int gameCount = Integer.parseInt(args[2]);
        Float optimalProbability = -1.f;
        if (aiName.equals("RandomContinuingProbabilityAIPlayer")){
            List<Float> probabilities = new ArrayList<>();
            float start = Float.parseFloat(args[3]);
            float stop = Float.parseFloat(args[4]);
            float step = Float.parseFloat(args[5]);
            for(float proba = start; proba <= stop; proba += step){
                probabilities.add(proba);
            }
            optimalProbability = new RandomContinuingProbabilityAIPlayerOptimizer().optimize(gameCount, probabilities);
        }
        else if (aiName.equals("FixContinueCountAIPlayer")){
            List<Integer> turns = new ArrayList<>();
            int start = Integer.parseInt(args[3]);
            int stop = Integer.parseInt(args[4]);
            int step = Integer.parseInt(args[5]);
            for(int turn = start; turn <= stop; turn += step){
                turns.add(turn);
            }
            optimalProbability = (float) new FixContinueCountAIPlayerOptimizer().optimize(gameCount, turns);
        }
        
        System.out.println("Optimal is " + optimalProbability);
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
