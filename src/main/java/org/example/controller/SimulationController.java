package org.example.controller;

import org.example.model.*;
import org.example.model.enums.Direction;
import org.example.model.enums.LightState;
import org.example.parser.JsonCommandParser;
import org.example.simulation.Simulation;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa zarządzająca wykonaniem symulacji ruchu drogowego.
 * Obsługuje ładowanie komend, wykonywanie kroków symulacji, dodawanie pojazdów i zapisywanie wyników.
 */
public class SimulationController {
    private final Intersection intersection;
    private final TrafficLightController trafficLightController;
    public final List<List<String>> simulationResults;
    private final JsonCommandParser parser;

    /**
     * Konstruktor kontrolera symulacji.
     * Inicjalizuje skrzyżowanie, kontroler świateł drogowych i parser JSON.
     *
     * @param simulation Obiekt symulacji, który będzie używany do aktualizacji świateł.
     */
    public SimulationController(Simulation simulation) {
        this.intersection = createDefaultIntersection();
        this.trafficLightController = new TrafficLightController(intersection, simulation);
        this.simulationResults = new ArrayList<>();
        this.parser = new JsonCommandParser();
    }

    /**
     * Laduje komendy symulacji z pliku JSON i wykonuje odpowiednie akcje.
     *
     * @param filePath Ścieżka do pliku JSON zawierającego komendy.
     * @throws IOException Jeśli wystąpi błąd podczas odczytu pliku.
     */
    public void runSimulation(String filePath) throws IOException, InterruptedException {
        List<Map<String, Object>> commands = parser.parseInput(filePath);

        for (Map<String, Object> command : commands) {
            String commandType = (String) command.get("type");

            switch (commandType) {
                case "addVehicle":
                    handleAddVehicle(command);
                    break;
                case "step":
                    processStep();
                    break;
                default:
                    System.out.println("Nieznana komenda: " + commandType);
            }
        }
    }

    /**
     * Wykonuje jeden krok symulacji.
     * Zaktualizowane zostają światła drogowe oraz stan pojazdów.
     */
    public void processStep() throws InterruptedException {
        trafficLightController.updateTrafficLights();

        List<String> vehiclesThatPassed = new ArrayList<>();
        for (Road road : intersection.getAllRoads().values()) {
            if (road.getTrafficLight().isGreen() && road.hasVehicles()) {
                System.out.println("Na drodze " + road.getDirection() +" jest " + road.getTrafficLight().getState() + " swiatlo");
                vehiclesThatPassed.add(road.removeVehicle().getVehicleId());
            }
        }
        System.out.println("pojazdy, ktore przejechaly to: " + vehiclesThatPassed);
        System.out.println("Czekam na kolejne komendy");
        Thread.sleep(3000);
        simulationResults.add(vehiclesThatPassed);
    }

    /**
     * Dodaje pojazd do odpowiedniej drogi na podstawie danych z komendy.
     *
     * @param command Komenda zawierająca dane pojazdu.
     */
    void handleAddVehicle(Map<String, Object> command) {
        String vehicleId = (String) command.get("vehicleId");
        Direction startRoad = Direction.valueOf(((String) command.get("startRoad")).toUpperCase());
        Direction endRoad = Direction.valueOf(((String) command.get("endRoad")).toUpperCase());

        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);

        Road road = intersection.getRoad(startRoad);
        if (road != null) {
            road.addVehicle(vehicle);
        } else {
            System.out.println("Nieznana droga: " + startRoad);
        }
    }

    /**
     * Zapisuje wyniki symulacji do pliku JSON.
     *
     * @param outputFilePath Ścieżka do pliku wyjściowego.
     * @throws IOException Jeśli wystąpi błąd podczas zapisu pliku.
     */
    public void saveResults(String outputFilePath) throws IOException {
        List<Map<String, List<String>>> stepStatuses = simulationResults.stream()
                .map(vehicles -> Collections.singletonMap("vehiclesLeft", vehicles))
                .collect(Collectors.toList());

        parser.writeOutput(outputFilePath, stepStatuses);
    }

    /**
     * Tworzy domyślne skrzyżowanie z czterema drogami i sygnalizacją świetlną.
     *
     * @return Skrzyżowanie z domyślnymi drogami i światłami.
     */
    private Intersection createDefaultIntersection() {
        Map<Direction, Road> roads = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(
                        direction -> direction,
                        direction -> new Road(direction, new TrafficLight(LightState.RED))
                ));
        return new Intersection(roads);
    }

    public Intersection getIntersection() {
        return this.intersection;
    }
}
