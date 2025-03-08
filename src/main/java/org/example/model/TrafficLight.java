package org.example.model;

import org.example.model.enums.LightState;

/**
 * Reprezentuje sygnalizator świetlny w symulacji ruchu drogowego.
 * Przechowuje stan sygnalizatora oraz pozwala na sprawdzanie jego koloru.
 */
public class TrafficLight {
    private LightState state;

    /**
     * Tworzy nowy sygnalizator świetlny o podanym początkowym stanie.
     *
     * @param initialState Początkowy stan sygnalizatora.
     */
    public TrafficLight(LightState initialState) {
        this.state = initialState;
    }

    /**
     * Zwraca bieżący stan sygnalizatora.
     *
     * @return Aktualny stan sygnalizatora.
     */
    public LightState getState() {
        return state;
    }

    /**
     * Ustawia nowy stan sygnalizatora.
     *
     * @param state Nowy stan sygnalizatora.
     */
    public void setState(LightState state) {
        this.state = state;
    }

    /**
     * Sprawdza, czy sygnalizator świeci na zielono.
     *
     * @return true, jeśli stan to zielony, false w przeciwnym razie.
     */
    public boolean isGreen() {
        return state == LightState.GREEN;
    }

    /**
     * Sprawdza, czy sygnalizator świeci na czerwono.
     *
     * @return true, jeśli stan to czerwony, false w przeciwnym razie.
     */
    public boolean isRed() {
        return state == LightState.RED;
    }
}
