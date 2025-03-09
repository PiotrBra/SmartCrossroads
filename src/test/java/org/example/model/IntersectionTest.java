package org.example.model;

import org.example.model.enums.Direction;
import org.example.model.enums.LightState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    private Intersection intersection;
    private Road northRoad;
    private Road southRoad;
    private Road eastRoad;
    private Road westRoad;

    @BeforeEach
    void setUp() {
        // Tworzymy drogi
        northRoad = new Road(Direction.NORTH, new TrafficLight(LightState.RED));
        southRoad = new Road(Direction.SOUTH, new TrafficLight(LightState.GREEN));
        eastRoad = new Road(Direction.EAST, new TrafficLight(LightState.RED));
        westRoad = new Road(Direction.WEST, new TrafficLight(LightState.GREEN));

        // Tworzymy mapę dróg
        Map<Direction, Road> roads = new HashMap<>();
        roads.put(Direction.NORTH, northRoad);
        roads.put(Direction.SOUTH, southRoad);
        roads.put(Direction.EAST, eastRoad);
        roads.put(Direction.WEST, westRoad);

        // Tworzymy skrzyżowanie
        intersection = new Intersection(roads);
    }

    @Test
    void testGetRoad() {
        // Given
        Direction direction = Direction.NORTH;

        // When
        Road road = intersection.getRoad(direction);

        // Then
        assertEquals(northRoad, road, "The road for the specified direction should be returned.");
    }

    @Test
    void testGetAllRoads() {
        // When
        Map<Direction, Road> allRoads = intersection.getAllRoads();

        // Then
        assertEquals(4, allRoads.size(), "There should be 4 roads in the intersection.");
        assertTrue(allRoads.containsKey(Direction.NORTH), "The map should contain the road for the NORTH direction.");
        assertTrue(allRoads.containsKey(Direction.SOUTH), "The map should contain the road for the SOUTH direction.");
        assertTrue(allRoads.containsKey(Direction.EAST), "The map should contain the road for the EAST direction.");
        assertTrue(allRoads.containsKey(Direction.WEST), "The map should contain the road for the WEST direction.");
    }

    @Test
    public void testGetRoadWithInvalidDirection() {
        // Test na próbę przekazania niewłaściwego kierunku
        assertThrows(IllegalArgumentException.class, () -> {
            intersection.getRoad(Direction.valueOf("NORTHEAST"));  // NORTHEAST nie istnieje
        });
    }

}
