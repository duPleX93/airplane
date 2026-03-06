package com.airplane.dto;

import java.util.List;

public class RouteSegmentDto {
    private String flightId;
    private String fromCityName;
    private String toCityName;
    private String airlineName;
    private int distanceKm;
    private String durationFormatted;
    private int durationMinutes;
    private String departureTimeFormatted;
    private String arrivalTimeFormatted;
    private Integer waitMinutes; // null for first segment

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public String getFromCityName() { return fromCityName; }
    public void setFromCityName(String fromCityName) { this.fromCityName = fromCityName; }
    public String getToCityName() { return toCityName; }
    public void setToCityName(String toCityName) { this.toCityName = toCityName; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    public int getDistanceKm() { return distanceKm; }
    public void setDistanceKm(int distanceKm) { this.distanceKm = distanceKm; }
    public String getDurationFormatted() { return durationFormatted; }
    public void setDurationFormatted(String durationFormatted) { this.durationFormatted = durationFormatted; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getDepartureTimeFormatted() { return departureTimeFormatted; }
    public void setDepartureTimeFormatted(String departureTimeFormatted) { this.departureTimeFormatted = departureTimeFormatted; }
    public String getArrivalTimeFormatted() { return arrivalTimeFormatted; }
    public void setArrivalTimeFormatted(String arrivalTimeFormatted) { this.arrivalTimeFormatted = arrivalTimeFormatted; }
    public Integer getWaitMinutes() { return waitMinutes; }
    public void setWaitMinutes(Integer waitMinutes) { this.waitMinutes = waitMinutes; }
}
