package com.airplane.domain;

public class Airline {
    private final String id;
    private final String name;

    public Airline(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
