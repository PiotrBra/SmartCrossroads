package org.example.controller;

import org.example.model.Intersection;
import org.example.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightControllerTest {

    private TrafficLightController controller;
    private Intersection mockIntersection;
    private Simulation mockSimulation;

    @BeforeEach
    public void setUp() {
        // Tworzymy mocki dla Intersection i Simulation
        mockIntersection = mock(Intersection.class);
        mockSimulation = mock(Simulation.class);

        // Inicjalizujemy kontroler
        controller = new TrafficLightController(mockIntersection, mockSimulation);
    }


    @Test
    public void testUpdateTrafficLights_shouldCallSimulationUpdateLights() {
        // Wywołanie metody updateTrafficLights
        controller.updateTrafficLights();

        // Weryfikacja, czy metoda updateLights na symulacji została wywołana
        verify(mockSimulation).updateLights(mockIntersection);
    }
}
