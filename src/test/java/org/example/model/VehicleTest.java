package org.example.model;

import org.example.model.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testVehicleCreation() {
        // Given
        String vehicleId = "V123";
        Direction startRoad = Direction.NORTH;
        Direction endRoad = Direction.SOUTH;

        // When
        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);

        // Then
        assertEquals(vehicleId, vehicle.getVehicleId(), "Vehicle ID should be correct");
    }

    @Test
    void testVehicleWithDifferentStartAndEndRoad() {
        // Given
        String vehicleId = "V124";
        Direction startRoad = Direction.EAST;
        Direction endRoad = Direction.WEST;

        // When
        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);

        // Then
        assertEquals(vehicleId, vehicle.getVehicleId(), "Vehicle ID should be correct");
        assertNotNull(vehicle.getVehicleId(), "Vehicle should have an ID");
    }

    @Test
    void testVehicleWithSameStartAndEndRoad() {
        // Given
        String vehicleId = "V125";
        Direction startRoad = Direction.NORTH;
        Direction endRoad = Direction.NORTH;

        // When
        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);

        // Then
        assertEquals(vehicleId, vehicle.getVehicleId(), "Vehicle ID should be correct");
    }
}
