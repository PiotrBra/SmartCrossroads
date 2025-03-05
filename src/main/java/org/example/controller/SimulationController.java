package org.example.controller;

import org.example.model.Intersection;
import org.example.model.Vehicle;
import org.example.parser.JsonCommandParser;

import java.util.List;
import java.util.Map;
import java.io.IOException;

public class SimulationController {
    private final Intersection intersection;
    private final JsonCommandParser parser;

    public SimulationController() {
        intersection = new Intersection();
        parser = new JsonCommandParser();
    }

    // Metoda odpowiedzialna za wykonanie symulacji na podstawie pliku wejściowego i zapis wyniku do pliku wyjściowego
    public void runSimulation(String inputFile, String outputFile) {
        try {
            // Parsowanie poleceń z pliku JSON
            List<Map<String, Object>> commands = parser.parseInput(inputFile);
            // Lista statusów dla poszczególnych kroków symulacji
            List<Map<String, Object>> stepStatuses = new java.util.ArrayList<>();

            for(Map<String, Object> command : commands){
                String type = (String) command.get("type");
                if("addVehicle".equals(type)) {
                    String vehicleId = (String) command.get("vehicleId");
                    String startRoad = (String) command.get("startRoad");
                    String endRoad = (String) command.get("endRoad");
                    intersection.addVehicle(new Vehicle(vehicleId, startRoad, endRoad));
                } else if("step".equals(type)) {
                    List<String> leftVehicles = intersection.processStep();
                    stepStatuses.add(Map.of("leftVehicles", leftVehicles));
                }
            }
            // Zapis wyniku symulacji do pliku JSON
            parser.writeOutput(outputFile, stepStatuses);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
