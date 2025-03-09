package org.example.model;

import org.example.model.enums.LightState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {

    @Test
    void testInitialState() {
        // Given
        LightState initialState = LightState.RED;

        // When
        TrafficLight trafficLight = new TrafficLight(initialState);

        // Then
        assertEquals(initialState, trafficLight.getState(), "Initial state should be RED");
    }

    @Test
    void testSetGreenState() {
        // Given
        TrafficLight trafficLight = new TrafficLight(LightState.RED);

        // When
        trafficLight.setState(LightState.GREEN);

        // Then
        assertEquals(LightState.GREEN, trafficLight.getState(), "State should be GREEN after setting");
    }

    @Test
    void testIsGreen() {
        // Given
        TrafficLight trafficLight = new TrafficLight(LightState.GREEN);

        // When & Then
        assertTrue(trafficLight.isGreen(), "Traffic light should be green");
    }

    @Test
    void testIsRed() {
        // Given
        TrafficLight trafficLight = new TrafficLight(LightState.RED);

        // When & Then
        assertTrue(trafficLight.isRed(), "Traffic light should be red");
    }

    @Test
    void testIsGreenAfterStateChange() {
        // Given
        TrafficLight trafficLight = new TrafficLight(LightState.RED);

        // When
        trafficLight.setState(LightState.GREEN);

        // Then
        assertTrue(trafficLight.isGreen(), "Traffic light should be green after state change");
    }

    @Test
    void testIsRedAfterStateChange() {
        // Given
        TrafficLight trafficLight = new TrafficLight(LightState.GREEN);

        // When
        trafficLight.setState(LightState.RED);

        // Then
        assertTrue(trafficLight.isRed(), "Traffic light should be red after state change");
    }
}
