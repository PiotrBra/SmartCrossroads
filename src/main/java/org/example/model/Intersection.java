package org.example.model;


import org.example.model.enums.LightState;

import java.util.*;

public class Intersection {
    // Kolejki pojazdów dla każdej drogi
    private final Map<String, Queue<Vehicle>> roadQueues;
    // Światła dla każdej drogi
    private final Map<String, TrafficLight> trafficLights;

    public Intersection() {
        roadQueues = new HashMap<>();
        trafficLights = new HashMap<>();

        // Inicjalizacja dróg: north, south, east, west
        for(String road : Arrays.asList("north", "south", "east", "west")){
            roadQueues.put(road, new LinkedList<>());
            trafficLights.put(road, new TrafficLight());
        }

        // Na starcie przyjmujemy, że drogi north i south mają zielone światło
        trafficLights.get("north").setState(LightState.GREEN);
        trafficLights.get("south").setState(LightState.GREEN);
        trafficLights.get("east").setState(LightState.RED);
        trafficLights.get("west").setState(LightState.RED);
    }

    // Dodanie pojazdu do odpowiedniej kolejki
    public void addVehicle(Vehicle vehicle) {
        String road = vehicle.getStartRoad().toLowerCase();
        if(roadQueues.containsKey(road)){
            roadQueues.get(road).add(vehicle);
        } else {
            throw new IllegalArgumentException("Invalid road: " + road);
        }
    }

    // Przetwarzanie kroku symulacji – dla dróg z zielonym światłem przepuszcza jednego pojazdu
    public List<String> processStep() {
        List<String> leftVehicles = new ArrayList<>();

        // Dla każdej drogi z zielonym światłem – jeśli pojazd czeka, opuszcza skrzyżowanie
        for(String road : roadQueues.keySet()){
            TrafficLight light = trafficLights.get(road);
            if(light.getState() == LightState.GREEN && !roadQueues.get(road).isEmpty()){
                Vehicle vehicle = roadQueues.get(road).poll();
                leftVehicles.add(vehicle.getVehicleId());
            }
        }

        // Aktualizacja stanów świateł po kroku symulacji
        updateTrafficLights();
        return leftVehicles;
    }

    // Prosta strategia zmiany świateł: przełączanie między ruchem NS a EW
    private void updateTrafficLights() {
        boolean nsGreen = trafficLights.get("north").getState() == LightState.GREEN;
        if(nsGreen){
            // Ustaw NS na czerwone i EW na zielone
            trafficLights.get("north").setState(LightState.RED);
            trafficLights.get("south").setState(LightState.RED);
            trafficLights.get("east").setState(LightState.GREEN);
            trafficLights.get("west").setState(LightState.GREEN);
        } else {
            // Ustaw EW na czerwone i NS na zielone
            trafficLights.get("east").setState(LightState.RED);
            trafficLights.get("west").setState(LightState.RED);
            trafficLights.get("north").setState(LightState.GREEN);
            trafficLights.get("south").setState(LightState.GREEN);
        }
    }

    // W przyszłości można rozszerzyć tę metodę o adaptacyjny algorytm sterowania (np. zależny od liczby oczekujących pojazdów)
}

