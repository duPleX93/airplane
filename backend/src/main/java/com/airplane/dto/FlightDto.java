package com.airplane.dto;

public class FlightDto {
    private String id;
    private int distanceKm;
    private int durationMinutes;
    private String fromCityId;
    private String fromCityName;
    private String toCityId;
    private String toCityName;
    private String airlineId;
    private String airlineName;

    public FlightDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getDistanceKm() { return distanceKm; }
    public void setDistanceKm(int distanceKm) { this.distanceKm = distanceKm; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getFromCityId() { return fromCityId; }
    public void setFromCityId(String fromCityId) { this.fromCityId = fromCityId; }
    public String getFromCityName() { return fromCityName; }
    public void setFromCityName(String fromCityName) { this.fromCityName = fromCityName; }
    public String getToCityId() { return toCityId; }
    public void setToCityId(String toCityId) { this.toCityId = toCityId; }
    public String getToCityName() { return toCityName; }
    public void setToCityName(String toCityName) { this.toCityName = toCityName; }
    public String getAirlineId() { return airlineId; }
    public void setAirlineId(String airlineId) { this.airlineId = airlineId; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
}
