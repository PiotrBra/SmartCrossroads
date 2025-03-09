package org.example.model.enums;

public enum LightState {
    RED("czerwone"),
    YELLOW("żółte"),
    GREEN("zielone");

    private final String description;

    LightState(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
