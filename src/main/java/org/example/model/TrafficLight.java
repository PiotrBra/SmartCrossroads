package org.example.model;


import org.example.config.SimulationConfig;
import org.example.model.enums.LightState;

public class TrafficLight {
    private LightState state;
    private int timer;

    public TrafficLight() {
        // Domyślnie ustawiamy na czerwone – zmiana stanu nastąpi gdy skrzyżowanie je aktywuje
        this.state = LightState.RED;
        this.timer = 0;
    }

    public LightState getState() {
        return state;
    }

    public void setState(LightState state) {
        this.state = state;
    }

    // Prosty mechanizm zmiany stanów
    public void update() {
        if (timer > 0) {
            timer--;
        } else {
            switch (state) {
                case GREEN:
                    state = LightState.YELLOW;
                    timer = SimulationConfig.YELLOW_DURATION;
                    break;
                case YELLOW:
                    state = LightState.RED;
                    timer = 0; // Pozostaje czerwone aż do kolejnej aktywacji
                    break;
                case RED:
                    state = LightState.GREEN;
                    timer = SimulationConfig.MIN_GREEN_DURATION;
                    break;
            }
        }
    }
}
