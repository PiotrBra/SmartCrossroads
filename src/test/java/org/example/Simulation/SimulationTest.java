package org.example.Simulation;

import org.example.model.Intersection;
import org.example.model.Road;
import org.example.model.TrafficLight;
import org.example.model.Vehicle;
import org.example.model.enums.Direction;
import org.example.model.enums.LightState;
import org.example.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private Simulation simulation;
    private Intersection intersection;

    /**
     * Przygotowuje skrzyżowanie z 4 drogami, dla każdej domyślnie ustawiamy TrafficLight na RED.
     */
    @BeforeEach
    void setUp() {
        simulation = new Simulation();
        intersection = createIntersection();
    }

    /**
     * Tworzy skrzyżowanie z drogami dla wszystkich kierunków.
     */
    private Intersection createIntersection() {
        Map<Direction, Road> roads = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.values()) {
            // Domyślnie ustawiamy sygnalizację na RED
            roads.put(dir, new Road(dir, new TrafficLight(LightState.RED)));
        }
        return new Intersection(roads);
    }

    /**
     * Scenariusz 1:
     * Brak pojazdów na żadnej drodze – wszystkie światła powinny pozostać na RED, a currentGreenDirection ustawione na null.
     */
    @Test
    void testUpdateLights_NoVehicles() {
        simulation.updateLights(intersection);

        // Sprawdzamy, że dla każdej drogi światło jest RED.
        intersection.getAllRoads().values().forEach(road ->
                assertEquals(LightState.RED, road.getTrafficLight().getState(), "Wszystkie światła powinny być RED")
        );
    }

    /**
     * Scenariusz 2:
     * Na drogach SOUTH i NORTH pojawia się pojazd – metoda updateLights powinna ustawić zielone światła
     * dla tych dróg (oraz ich przeciwnych, czyli NORTH i SOUTH) i wybrać tę parę jako najruchliwszą.
     */
    @Test
    void testUpdateLights_VehiclesOnSouthNorth() {
        // Dodajemy pojazd na drogę SOUTH
        Road southRoad = intersection.getRoad(Direction.SOUTH);
        southRoad.addVehicle(new Vehicle("v1", Direction.SOUTH, Direction.NORTH));

        // Możemy też dodać pojazd na NORTH, aby sprawdzić sumaryczną liczbę oczekujących pojazdów
        Road northRoad = intersection.getRoad(Direction.NORTH);
        northRoad.addVehicle(new Vehicle("v2", Direction.NORTH, Direction.SOUTH));

        simulation.updateLights(intersection);

        // Oczekujemy, że drogi SOUTH i NORTH będą miały zielone światła,
        // a pozostałe pozostaną czerwone.
        assertEquals(LightState.GREEN, southRoad.getTrafficLight().getState(), "Droga SOUTH powinna mieć zielone światło");
        assertEquals(LightState.GREEN, northRoad.getTrafficLight().getState(), "Droga NORTH powinna mieć zielone światło");

        // Pozostałe drogi:
        intersection.getAllRoads().forEach((direction, road) -> {
            if (direction != Direction.SOUTH && direction != Direction.NORTH) {
                assertEquals(LightState.RED, road.getTrafficLight().getState(), "Droga " + direction + " powinna mieć czerwone światło");
            }
        });
    }

    /**
     * Scenariusz 3:
     * Testujemy sytuację, gdy na dwóch parach dróg pojazdy oczekują – wybierana jest ta, dla której suma pojazdów (danej drogi i jej przeciwnej) jest większa.
     */
    @Test
    void testUpdateLights_ChooseBusiestPair() {
        // Dodajemy pojazdy na dwie pary dróg:
        // Para SOUTH/NORTH: 1 pojazd
        intersection.getRoad(Direction.SOUTH).addVehicle(new Vehicle("v1", Direction.SOUTH, Direction.NORTH));

        // Para EAST/WEST: 2 pojazdy, czyli powinna być wybrana
        intersection.getRoad(Direction.EAST).addVehicle(new Vehicle("v2", Direction.EAST, Direction.WEST));
        intersection.getRoad(Direction.WEST).addVehicle(new Vehicle("v3", Direction.WEST, Direction.EAST));

        simulation.updateLights(intersection);

        // Oczekujemy, że drogi EAST i WEST mają zielone światła.
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.EAST).getTrafficLight().getState(), "Droga EAST powinna mieć zielone światło");
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.WEST).getTrafficLight().getState(), "Droga WEST powinna mieć zielone światło");

        // Pozostałe drogi powinny mieć czerwone światła.
        assertEquals(LightState.RED, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState(), "Droga SOUTH powinna mieć czerwone światło");
        assertEquals(LightState.RED, intersection.getRoad(Direction.NORTH).getTrafficLight().getState(), "Droga NORTH powinna mieć czerwone światło");
    }

    /**
     * Scenariusz 4:
     * Jeżeli aktualnie wybrana para dróg (np. SOUTH/NORTH) ma pojazdy oczekujące i nie osiągnięto maksymalnego czasu zielonego,
     * metoda updateLights powinna zwiększyć licznik kroków i pozostawić zielone światło.
     */
    @Test
    void testUpdateLights_MaintainGreenIfVehiclesStillWaiting() {
        // Dodajemy pojazd na SOUTH i NORTH
        intersection.getRoad(Direction.SOUTH).addVehicle(new Vehicle("v1", Direction.SOUTH, Direction.NORTH));
        intersection.getRoad(Direction.NORTH).addVehicle(new Vehicle("v2", Direction.NORTH, Direction.SOUTH));

        // Pierwsze wywołanie – powinno ustawić zielone światła dla SOUTH/NORTH
        simulation.updateLights(intersection);
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState());
        assertEquals(LightState.GREEN, intersection.getRoad(Direction.NORTH).getTrafficLight().getState());

        // Symulujemy kolejne wywołania updateLights, zakładając że pojazdy wciąż czekają.
        // Aby symulacja "utrzymała" zielony stan, pojazdy nie powinny zostać usunięte z kolejki.
        for (int i = 0; i < 3; i++) {
            simulation.updateLights(intersection);
            assertEquals(LightState.GREEN, intersection.getRoad(Direction.SOUTH).getTrafficLight().getState(), "Krok " + (i+2));
            assertEquals(LightState.GREEN, intersection.getRoad(Direction.NORTH).getTrafficLight().getState(), "Krok " + (i+2));
        }
    }

    /**
     * Scenariusz 5:
     * Jeśli aktualna para dróg była zielona, ale pojazdy zostały usunięte (lub nie ma już oczekujących),
     * metoda updateLights powinna zresetować licznik kroków i ustawić wszystkie światła na RED.
     */
    @Test
    void testUpdateLights_ResetWhenNoVehicles() {
        // Dodajemy pojazd na SOUTH/NORTH i ustawiamy zielone światła.
        Road southRoad = intersection.getRoad(Direction.SOUTH);
        Road northRoad = intersection.getRoad(Direction.NORTH);
        southRoad.addVehicle(new Vehicle("v1", Direction.SOUTH, Direction.NORTH));
        northRoad.addVehicle(new Vehicle("v2", Direction.NORTH, Direction.SOUTH));

        simulation.updateLights(intersection);
        assertEquals(LightState.GREEN, southRoad.getTrafficLight().getState());
        assertEquals(LightState.GREEN, northRoad.getTrafficLight().getState());

        // Usuwamy pojazdy z tych dróg.
        southRoad.removeVehicle();
        northRoad.removeVehicle();

        // Wywołanie updateLights powinno wykryć brak oczekujących pojazdów i ustawić wszystkie światła na RED.
        simulation.updateLights(intersection);
        intersection.getAllRoads().values().forEach(road ->
                assertEquals(LightState.RED, road.getTrafficLight().getState(), "Po usunięciu pojazdów wszystkie światła powinny być RED")
        );
    }
}
