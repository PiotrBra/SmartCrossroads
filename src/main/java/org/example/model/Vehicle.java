package org.example.model;

import org.example.model.enums.Direction;

/**
 * Reprezentuje pojazd w symulacji.
 * Posiada identyfikator oraz informacje o drogach początkowej i docelowej.
 */
public class Vehicle {
    private final String vehicleId;


    private final Direction startRoad;
    private final Direction endRoad;

    /**
     * Tworzy nowy pojazd.
     *
     * @param vehicleId Identyfikator pojazdu.
     * @param startRoad Droga początkowa.
     * @param endRoad Droga docelowa.
     */
    public Vehicle(String vehicleId, Direction startRoad, Direction endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    /**
     * Zwraca identyfikator pojazdu.
     *
     * @return Identyfikator pojazdu.
     */
    public String getVehicleId() {
        return vehicleId;
    }

}
