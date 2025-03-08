package org.example.controller;

import org.example.model.Intersection;
import org.example.simulation.Simulation;

/**
 * Klasa kontrolująca sygnalizację świetlną na skrzyżowaniu.
 * Odpowiada za aktualizowanie stanu świateł zgodnie z przyjętą strategią symulacyjną.
 */
public class TrafficLightController {
    private final Intersection intersection;
    private final Simulation simulation;

    /**
     * Konstruktor kontrolera sygnalizacji świetlnej.
     *
     * @param intersection Skrzyżowanie, na którym zarządzane są światła.
     * @param simulation Obiekt symulacji, który zarządza stanami świateł.
     */
    public TrafficLightController(Intersection intersection, Simulation simulation) {
        this.intersection = intersection;
        this.simulation = simulation;
    }

    /**
     * Aktualizuje stany świateł na skrzyżowaniu na podstawie strategii symulacji.
     */
    public void updateTrafficLights() {
        simulation.updateLights(intersection);
    }
}
