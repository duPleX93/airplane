package com.airplane.domain;

public class Flight {
    private final String id;
    private final int distanceKm;
    private final int durationMinutes;
    private final City fromCity;
    private final City toCity;
    private final Airline airline;

    public Flight(String id, int distanceKm, int durationMinutes, City fromCity, City toCity, Airline airline) {
        this.id = id;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.airline = airline;
    }

    public String getId() {
        return id;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public City getFromCity() {
        return fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public Airline getAirline() {
        return airline;
    }
}
