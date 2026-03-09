package com.airplane.domain;

import java.util.Objects;

public class City {
    private final String id;
    private final String name;
    private final int population;

    public City(String id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }
}
