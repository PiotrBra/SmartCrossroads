package org.example.model;

import org.example.model.enums.Direction;

import java.util.*;

/**
 * Reprezentuje drogę w symulacji ruchu drogowego.
 * Zawiera informacje o kierunku, sygnalizatorze oraz pojazdach oczekujących na przejazd.
 */
public class Road {
    private final Direction direction;
    private final Queue<Vehicle> waitingVehicles;
    private TrafficLight trafficLight;

    /**
     * Tworzy nową drogę z określonym kierunkiem i sygnalizatorem świetlnym.
     *
     * @param direction Kierunek drogi.
     * @param trafficLight Sygnalizator świetlny przypisany do drogi.
     */
    public Road(Direction direction, TrafficLight trafficLight) {
        this.direction = direction;
        this.trafficLight = trafficLight;
        this.waitingVehicles = new LinkedList<>();
    }

    /**
     * Zwraca kierunek drogi.
     *
     * @return Kierunek drogi.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Zwraca sygnalizator świetlny przypisany do drogi.
     *
     * @return Sygnalizator świetlny.
     */
    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    /**
     * Ustawia nowy sygnalizator świetlny dla drogi.
     *
     * @param trafficLight Nowy sygnalizator świetlny.
     */
    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    /**
     * Dodaje pojazd do kolejki pojazdów oczekujących na przejazd.
     *
     * @param vehicle Pojazd do dodania.
     */
    public void addVehicle(Vehicle vehicle) {
        waitingVehicles.add(vehicle);
    }

    /**
     * Usuwa pierwszy pojazd z kolejki oczekujących pojazdów.
     *
     * @return Pojazd, który został usunięty z kolejki.
     */
    public Vehicle removeVehicle() {
        return waitingVehicles.poll();
    }

    /**
     * Sprawdza, czy na drodze są pojazdy oczekujące na przejazd.
     *
     * @return true, jeśli są pojazdy w kolejce, false w przeciwnym razie.
     */
    public boolean hasVehicles() {
        return !waitingVehicles.isEmpty();
    }

    /**
     * Zwraca kolejkę pojazdów oczekujących na przejazd.
     *
     * @return Kolejka pojazdów.
     */
    public Queue<Vehicle> getWaitingVehicles() {
        return waitingVehicles;
    }
}
