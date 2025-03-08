package org.example.simulation;

import org.example.model.Intersection;
import org.example.model.Road;
import org.example.model.enums.Direction;
import org.example.model.enums.LightState;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa symulująca zarządzanie światłami drogowymi na skrzyżowaniu.
 * Na podstawie liczby pojazdów i czasu trwania zielonego światła, ustawia odpowiednie sygnalizacje.
 */
public class Simulation {

    private static final int MAX_GREEN_DURATION = 5;  // Maksymalna liczba kroków przed zmianą świateł
    private Direction currentGreenDirection = null;
    private int stepsElapsed = 0;

    public Simulation() {}

    /**
     * Aktualizuje stan świateł drogowych w skrzyżowaniu na podstawie liczby pojazdów
     * i czasu trwania zielonego światła.
     *
     * @param intersection Skrzyżowanie, którego światła mają zostać zaktualizowane.
     */
    public void updateLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getAllRoads();

        // Jeśli aktualne światło jest zielone, sprawdzamy, czy pojazdy nadal czekają
        if (currentGreenDirection != null && stepsElapsed < MAX_GREEN_DURATION) {
            Road activeRoad = roads.get(currentGreenDirection);
            Road oppositeRoad = roads.get(getOppositeDirection(currentGreenDirection));

            // Utrzymaj zielone, jeśli pojazdy są obecne
            if (activeRoad.hasVehicles() || oppositeRoad.hasVehicles()) {
                stepsElapsed++;
                return;
            }
        }

        // Zresetuj licznik kroków przy zmianie świateł
        stepsElapsed = 0;

        // Ustaw wszystkie światła na czerwone
        roads.values().forEach(road -> road.getTrafficLight().setState(LightState.RED));

        // Wybierz drogę z największą liczbą oczekujących pojazdów
        Optional<Direction> busiestPair = findBusiestRoadPair(roads);

        // Jeśli nie ma pojazdów, zachowaj wszystkie światła na czerwonym
        if (busiestPair.isEmpty() || areAllRoadsEmpty(roads, busiestPair.get())) {
            currentGreenDirection = null;
            return;
        }

        // Ustaw zieloną sygnalizację i zacznij liczyć kroki
        busiestPair.ifPresent(direction -> {
            roads.get(direction).getTrafficLight().setState(LightState.GREEN);
            roads.get(getOppositeDirection(direction)).getTrafficLight().setState(LightState.GREEN);
            currentGreenDirection = direction;
        });
    }

    /**
     * Znajduje parę dróg z największą liczbą oczekujących pojazdów.
     *
     * @param roads Mapę dróg w skrzyżowaniu.
     * @return Kierunek z największą liczbą oczekujących pojazdów.
     */
    private Optional<Direction> findBusiestRoadPair(Map<Direction, Road> roads) {
        return roads.keySet().stream()
                .max(Comparator.comparingInt(dir -> countWaitingVehicles(roads, dir)));
    }

    /**
     * Liczy łączną liczbę oczekujących pojazdów na drodze i jej przeciwnym kierunku.
     *
     * @param roads Mapa dróg.
     * @param direction Kierunek, dla którego liczymy pojazdy.
     * @return Liczba oczekujących pojazdów.
     */
    private int countWaitingVehicles(Map<Direction, Road> roads, Direction direction) {
        return roads.get(direction).getWaitingVehicles().size() +
                roads.get(getOppositeDirection(direction)).getWaitingVehicles().size();
    }

    /**
     * Sprawdza, czy na drogach nie ma pojazdów oczekujących.
     *
     * @param roads Mapa dróg.
     * @param direction Kierunek drogi, który sprawdzamy.
     * @return Zwraca prawdę, jeśli na obu drogach nie ma pojazdów.
     */
    private boolean areAllRoadsEmpty(Map<Direction, Road> roads, Direction direction) {
        return roads.get(direction).getWaitingVehicles().isEmpty() &&
                roads.get(getOppositeDirection(direction)).getWaitingVehicles().isEmpty();
    }

    /**
     * Zwraca przeciwny kierunek do danego.
     *
     * @param direction Kierunek, którego przeciwny kierunek chcemy znaleźć.
     * @return Przeciwny kierunek.
     */
    private Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
            default: throw new IllegalArgumentException("Nieznany kierunek: " + direction);
        }
    }
}
