package org.example.model;

import org.example.model.enums.Direction;

import java.util.Map;

/**
 * Reprezentuje skrzyżowanie dróg w symulacji ruchu drogowego.
 * Zawiera mapę dróg przypisanych do określonych kierunków.
 */
public class Intersection {
    private final Map<Direction, Road> roads;

    /**
     * Tworzy skrzyżowanie z określoną mapą dróg.
     *
     * @param roads Mapa dróg przypisanych do odpowiednich kierunków.
     */
    public Intersection(Map<Direction, Road> roads) {
        this.roads = roads;
    }

    /**
     * Zwraca drogę przypisaną do określonego kierunku.
     *
     * @param direction Kierunek, dla którego chcemy uzyskać drogę.
     * @return Droga przypisana do danego kierunku.
     */
    public Road getRoad(Direction direction) {
        return roads.get(direction);
    }

    /**
     * Zwraca mapę wszystkich dróg w skrzyżowaniu.
     *
     * @return Mapa dróg przypisanych do kierunków.
     */
    public Map<Direction, Road> getAllRoads() {
        return roads;
    }
}
