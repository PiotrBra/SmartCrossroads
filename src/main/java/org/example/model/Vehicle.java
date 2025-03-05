package org.example.model;

public class Vehicle {
    private final String vehicleId;
    private final String startRoad;
    private final String endRoad;

    public Vehicle(String vehicleId, String startRoad, String endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getStartRoad() {
        return startRoad;
    }

    public String getEndRoad() {
        return endRoad;
    }
}
