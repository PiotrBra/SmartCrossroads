package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


import org.example.controller.SimulationController;

public class Main {
    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Usage: java -jar simulation.jar <input.json> <output.json>");
            System.exit(1);
        }
        String inputFile = args[0];
        String outputFile = args[1];

        SimulationController controller = new SimulationController();
        controller.runSimulation(inputFile, outputFile);
    }
}
