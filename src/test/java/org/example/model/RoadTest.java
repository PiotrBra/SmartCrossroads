package org.example.model;

import org.example.model.enums.Direction;
import org.example.model.enums.LightState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {

    private Road road;
    private TrafficLight trafficLight;

    @BeforeEach
    void setUp() {
        // Przygotowanie obiektu Road i TrafficLight przed każdym testem
        trafficLight = new TrafficLight(LightState.RED);
        road = new Road(Direction.NORTH, trafficLight);
    }

    @Test
    void testAddVehicle() {
        // Given
        Vehicle vehicle = new Vehicle("V1", Direction.NORTH, Direction.SOUTH);

        // When
        road.addVehicle(vehicle);

        // Then
        assertEquals(1, road.getWaitingVehicles().size(), "Queue should contain one vehicle.");
        assertTrue(road.getWaitingVehicles().contains(vehicle), "Vehicle should be in the queue.");
    }

    @Test
    void testRemoveVehicle() {
        // Given
        Vehicle vehicle = new Vehicle("V1", Direction.NORTH, Direction.SOUTH);
        road.addVehicle(vehicle);

        // When
        Vehicle removedVehicle = road.removeVehicle();

        // Then
        assertEquals(vehicle, removedVehicle, "The removed vehicle should be the same as the one added.");
        assertTrue(road.getWaitingVehicles().isEmpty(), "Queue should be empty after removing the vehicle.");
    }

    @Test
    void testHasVehiclesWhenQueueIsNotEmpty() {
        // Given
        Vehicle vehicle = new Vehicle("V1", Direction.NORTH, Direction.SOUTH);
        road.addVehicle(vehicle);

        // When & Then
        assertTrue(road.hasVehicles(), "Road should have vehicles when the queue is not empty.");
    }

    @Test
    void testHasVehiclesWhenQueueIsEmpty() {
        // Given
        // No vehicles added

        // When & Then
        assertFalse(road.hasVehicles(), "Road should not have vehicles when the queue is empty.");
    }

    @Test
    void testGetTrafficLight() {
        // Given
        TrafficLight light = road.getTrafficLight();

        // Then
        assertEquals(trafficLight, light, "The traffic light should be the one initially set.");
    }

    @Test
    void testSetTrafficLight() {
        // Given
        TrafficLight newTrafficLight = new TrafficLight(LightState.GREEN);

        // When
        road.setTrafficLight(newTrafficLight);

        // Then
        assertEquals(newTrafficLight, road.getTrafficLight(), "The traffic light should be updated.");
    }

    @Test
    void testInitialWaitingVehicles() {
        // Given
        // No vehicles added

        // When & Then
        assertTrue(road.getWaitingVehicles().isEmpty(), "Initially, the waiting vehicle queue should be empty.");
    }

    @Test
    void testAddMultipleVehicles() {
        // Given
        Vehicle vehicle1 = new Vehicle("V1", Direction.NORTH, Direction.SOUTH);
        Vehicle vehicle2 = new Vehicle("V2", Direction.NORTH, Direction.SOUTH);

        // When
        road.addVehicle(vehicle1);
        road.addVehicle(vehicle2);

        // Then
        assertEquals(2, road.getWaitingVehicles().size(), "Queue should contain two vehicles.");
        assertTrue(road.getWaitingVehicles().contains(vehicle1), "Queue should contain the first vehicle.");
        assertTrue(road.getWaitingVehicles().contains(vehicle2), "Queue should contain the second vehicle.");
    }
}
