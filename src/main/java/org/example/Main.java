package org.example;

import org.example.controller.SimulationController;
import org.example.simulation.Simulation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 2){
            System.out.println("Usage:  java -jar build/libs/SmartCrossroads-1.0.jar input.json output.json");
            System.exit(1);
        }
        String inputFile = args[0];
        String outputFile = args[1];
        SimulationController simulationController = new SimulationController(new Simulation());
        simulationController.runSimulation(inputFile);

        simulationController.saveResults(outputFile);
    }
}
